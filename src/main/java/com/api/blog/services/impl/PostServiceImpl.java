package com.api.blog.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog.domain.Post;
import com.api.blog.domain.Usuario;
import com.api.blog.exception.domain.post.PostNaoEncontradoException;
import com.api.blog.exception.domain.post.PostNaoPertenceAoUsuarioException;
import com.api.blog.repository.PostRepository;
import com.api.blog.services.PostService;
import com.api.blog.services.UsuarioService;

@Service
@Transactional
@Qualifier("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public List<Post> listarTodos() {
		return repository.findAll();
	}

	@Override
	public List<Post> listarPorUsuario(Long id) {
		return repository.findAllByUsuarioId(id);
	}

	@Override
	public Post salvar(Post post) {
		return repository.save(post);
	}

	@Override
	public Post atualizar(Post post, Long id) throws PostNaoEncontradoException {
		Optional<Post> optional = repository.findById(id);
		if (optional.isEmpty()) {
			throw new PostNaoEncontradoException("Post não encontrado");
		}
		post.setId(id);
		post.setDataComentario(optional.get().getDataComentario());
		return repository.save(post);
	}

	@Override
	public void deletar(Long idPost) throws PostNaoPertenceAoUsuarioException, PostNaoEncontradoException {
		Usuario usuario = buscarUsuarioDeSecao();
		Optional<Post> post = repository.findById(idPost);
		if (post.isEmpty()) {
			throw new PostNaoEncontradoException("Post não encontrado");
		}
		if (post.isPresent() && usuario != null && post.get().getUsuario().getId().equals(usuario.getId())) {
			repository.deleteById(idPost);
		} else {
			throw new PostNaoPertenceAoUsuarioException("Post não pertence ao usuário");
		}
	}

	private Usuario buscarUsuarioDeSecao() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioService.buscaPorNome(principal.toString());
	}

}
