package com.api.blog.services;

import java.util.List;

import com.api.blog.domain.Usuario;
import com.api.blog.exception.domain.usuario.EmailExistenteException;
import com.api.blog.exception.domain.usuario.UsuarioCadastradoException;
import com.api.blog.exception.domain.usuario.UsuarioNaoEncontradoException;

public interface UsuarioService {

	Usuario salvar(Usuario usuario) throws UsuarioNaoEncontradoException, UsuarioCadastradoException, EmailExistenteException;
	
	List<Usuario> listarTodos();
	
	Usuario buscaPorNome(String nome);
	
	Usuario buscaPorEmail(String email);
}
