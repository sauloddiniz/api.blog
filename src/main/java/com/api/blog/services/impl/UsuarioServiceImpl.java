package com.api.blog.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog.domain.UserDetail;
import com.api.blog.domain.Usuario;
import com.api.blog.enums.Role;
import com.api.blog.exception.domain.EmailExistenteException;
import com.api.blog.exception.domain.UsuarioCadastradoException;
import com.api.blog.exception.domain.UsuarioNaoEncontradoException;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByNome(username);
		if (usuario == null) {
			LOGGER.error("Usuario não encontrado" + username);
			throw new UsernameNotFoundException("Usuário não encontrado" + username);
		} else {
			usuario.setLastLoginDateDisplay(usuario.getLastLoginDate());
			usuario.setLastLoginDate(new Date());
			repository.save(usuario);
			UserDetail uDetail = new UserDetail(usuario);
			LOGGER.info("Usuario encontrado" + username);
			return uDetail;
		}
	}

	@Override
	public Usuario salvar(String nome, String email, String senha) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException {
		validarUsuarioAndEmail(nome, email);
		Usuario usuario = new Usuario();
		String usuarioId = gerarIdUsuario();
		String encodePassword = encoder(senha);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setUsuarioId(usuarioId);
		usuario.setSenha(encodePassword);
		usuario.setJoinDate(new Date());
		usuario.setActive(true);
		usuario.setNotLocked(true);
		usuario.setRole(Role.ROLE_ADMIN.name());
		usuario.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
		repository.save(usuario);
		LOGGER.info("new user password: " + senha);
		return null;
	}

	private String gerarIdUsuario() {
		return "Toma no cu";
	}

	private Usuario validarUsuarioAndEmail(String nome, String email) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException {
		if(StringUtils.isNotBlank(nome)) {
			Usuario usuario = findByNome(nome);
			if(usuario != null) {
				throw new UsuarioCadastradoException("usuário já cadastrado");
			}
			Usuario usuarioEmail = findByEmail(email);
			if(usuarioEmail != null) {
				throw new EmailExistenteException("E-mail já cadastrado");
			}
			return usuario;
		} else {
			return null;
		}
		
	}

	@Override
	public List<Usuario> findAll() {
		return repository.findAll();
	}

	@Override
	public Usuario findByNome(String nome) {
		return repository.findByNome(nome);
	}

	@Override
	public Usuario findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	private String encoder(String senha) {
		return passwordEncoder.encode(senha); 
	}

}
