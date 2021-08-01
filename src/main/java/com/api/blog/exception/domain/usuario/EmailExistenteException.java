package com.api.blog.exception.domain.usuario;

public class EmailExistenteException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailExistenteException(String msg) {
		super(msg);
	}
}
