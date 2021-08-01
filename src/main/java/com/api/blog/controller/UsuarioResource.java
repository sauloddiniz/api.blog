package com.api.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.constant.SecurityConstant;
import com.api.blog.domain.UserDetail;
import com.api.blog.domain.Usuario;
import com.api.blog.exception.domain.ExceptionHandling;
import com.api.blog.exception.domain.usuario.EmailExistenteException;
import com.api.blog.exception.domain.usuario.UsuarioCadastradoException;
import com.api.blog.exception.domain.usuario.UsuarioNaoEncontradoException;
import com.api.blog.services.UsuarioService;
import com.api.blog.utility.JWTTokenProvider;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource extends ExceptionHandling {

	@Autowired
	private UsuarioService service;

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JWTTokenProvider tokenProvider;
	
	@PostMapping("/salvar")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario)
			throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException {
		Usuario usuarioLogado = service.salvar(usuario);
		return new ResponseEntity<>(usuarioLogado, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
		authenticated(usuario.getNome(), usuario.getSenha());
		Usuario userLogin = service.buscaPorNome(usuario.getNome());
		UserDetail detail = new UserDetail(userLogin);
		HttpHeaders jwtHeader = criarJwtHeader(detail);
		return new ResponseEntity<>(userLogin, jwtHeader, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
		return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
	}
	
	private HttpHeaders criarJwtHeader(UserDetail detail) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, tokenProvider.geraJwtToken(detail));
		return headers;
	}

	private void authenticated(String nome, String senha) {
		manager.authenticate(new UsernamePasswordAuthenticationToken(nome, senha));
	}
}
