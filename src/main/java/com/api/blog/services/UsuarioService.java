package com.api.blog.services;

import java.util.List;

import com.api.blog.domain.Usuario;
import com.api.blog.exception.domain.EmailExistenteException;
import com.api.blog.exception.domain.UsuarioCadastradoException;
import com.api.blog.exception.domain.UsuarioNaoEncontradoException;

public interface UsuarioService {

	Usuario salvar(String nome, String email, String senha) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException;
	
	List<Usuario> findAll();
	
	Usuario findByNome(String nome);
	
	Usuario findByEmail(String email);
}
