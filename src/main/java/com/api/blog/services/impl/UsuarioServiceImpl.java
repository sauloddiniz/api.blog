package com.api.blog.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog.domain.UserDetail;
import com.api.blog.domain.Usuario;
import com.api.blog.exception.domain.usuario.EmailExistenteException;
import com.api.blog.exception.domain.usuario.UsuarioCadastradoException;
import com.api.blog.exception.domain.usuario.UsuarioNaoEncontradoException;
import com.api.blog.repository.UsuarioRepository;
import com.api.blog.services.UsuarioService;

@Service
@Transactional
@Qualifier("UsuarioDetailService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
		Usuario usuario = buscaPorNome(nome);
		if (usuario == null) {
			LOGGER.error("Usuario não encontrado" + nome);
			throw new UsernameNotFoundException("Usuário não encontrado" + nome);
		} else {
			usuario.setDtUltimoLogin(new Date());
			repository.save(usuario);
			UserDetail uDetail = new UserDetail(usuario);
			LOGGER.info("Usuario encontrado" + nome);
			return uDetail;
		}
	}

	@Override
	public Usuario salvar(Usuario usuario) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException {
		validarUsuarioAndEmail(usuario.getNome(), usuario.getEmail());
		String encodePassword = encoder(usuario.getSenha());
		usuario.setSenha(encodePassword);
		usuario.setDtUltimoLogin(new Date());
		usuario.setAtivo(true);
		usuario.setNaoBloqueado(true);
		usuario.setRole(usuario.getRole());
		usuario.setAuthorities(usuario.getAuthorities());
		repository.save(usuario);
		LOGGER.info("new user password: " + usuario.getSenha());
		return usuario;
	}

	private Usuario validarUsuarioAndEmail(String nome, String email) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException {
		if(StringUtils.isNotBlank(nome)) {
			Usuario usuario = buscaPorNome(nome);
			if(usuario != null) {
				throw new UsuarioCadastradoException("usuário já cadastrado");
			}
			Usuario usuarioEmail = buscaPorEmail(email);
			if(usuarioEmail != null) {
				throw new EmailExistenteException("E-mail já cadastrado");
			}
			return usuario;
		} else {
			return null;
		}
		
	}

	@Override
	public List<Usuario> listarTodos() {
		return repository.findAll();
	}

	@Override
	public Usuario buscaPorNome(String nome) {
		return repository.findByNome(nome);
	}

	@Override
	public Usuario buscaPorEmail(String email) {
		return repository.findByEmail(email);
	}
	
	private String encoder(String senha) {
		return passwordEncoder.encode(senha); 
	}

}
