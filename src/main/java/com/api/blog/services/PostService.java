package com.api.blog.services;

import java.util.List;

import com.api.blog.domain.Post;
import com.api.blog.exception.domain.post.PostNaoEncontradoException;
import com.api.blog.exception.domain.post.PostNaoPertenceAoUsuarioException;

public interface PostService {

	List<Post> listarTodos();

	List<Post> listarPorUsuario(Long id);

	Post salvar(Post post);
	
	Post atualizar(Post post, Long id) throws PostNaoEncontradoException;

	void deletar(Long idPost) throws PostNaoPertenceAoUsuarioException, PostNaoEncontradoException;

}
