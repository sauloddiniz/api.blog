package com.api.blog.exception.domain.post;

public class PostNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public PostNaoEncontradoException(String msg) {
		super(msg);
	}

}
