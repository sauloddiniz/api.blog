package com.api.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.domain.Post;
import com.api.blog.exception.domain.post.PostNaoEncontradoException;
import com.api.blog.exception.domain.post.PostNaoPertenceAoUsuarioException;
import com.api.blog.services.PostService;

@RestController
@RequestMapping(value = "/post")
public class PostResource {

	@Autowired
	private PostService service;
	
	@PostMapping("/salvar")
	public ResponseEntity<Post> salvar(@RequestBody Post post) {
		return new ResponseEntity<>(service.salvar(post), HttpStatus.CREATED);
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Post> atualizar(@RequestBody Post post, @PathVariable Long id) throws PostNaoEncontradoException {
		return new ResponseEntity<>(service.atualizar(post, id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> listarTodos() {
		return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<Post>> listarPorUsuario(@PathVariable Long id) {
		return new ResponseEntity<>(service.listarPorUsuario(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Post> delete(@PathVariable Long id)
			throws PostNaoPertenceAoUsuarioException, PostNaoEncontradoException {
		service.deletar(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
