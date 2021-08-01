package com.api.blog.exception.domain;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.api.blog.domain.HttpResponse;
import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class ExceptionHandling {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String ACCOUNT_LOCKED = "Your account has ben locked. Please contact administration";
	private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowrd on this endpoint. Please send a '%s' request";
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occrred while processing the request";
	private static final String INCORRECT_CREDENTIAL = "UserName/Password incorrect. Please try again";
	private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is as error, please contact administration";
	private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
	private static final String NOT_ENOUGH_PERMISSION = "You do not have enoug permission";
	private static final String PAGE_NOT_FOUND = "This page was not found";

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<HttpResponse> accountDisabledException() {
		return createHttpRespose(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<HttpResponse> badCredencialsException() {
		return createHttpRespose(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIAL);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedException() {
		return createHttpRespose(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<HttpResponse> lockedExpection() {
		return createHttpRespose(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
		return createHttpRespose(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}
	
	@ExceptionHandler(EmailExistenteException.class)
	public ResponseEntity<HttpResponse> emailExistException(EmailExistenteException exception) {
		return createHttpRespose(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(EmailNaoEncontradoException.class)
	public ResponseEntity<HttpResponse> emailNoExistException(EmailNaoEncontradoException exception) {
		return createHttpRespose(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(UsuarioCadastradoException.class)
	public ResponseEntity<HttpResponse> emailExistException(UsuarioCadastradoException exception) {
		return createHttpRespose(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<HttpResponse> emailNoExistException(UsuarioNaoEncontradoException exception) {
		return createHttpRespose(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException exception) {
		return createHttpRespose(HttpStatus.BAD_REQUEST, PAGE_NOT_FOUND);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> methodNotSuportedException(HttpRequestMethodNotSupportedException exception) {
		HttpMethod httpMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
		return createHttpRespose(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, httpMethod));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
		LOGGER.error(exception.getMessage());
		return createHttpRespose(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpRespose(HttpStatus.NOT_FOUND, exception.getMessage());
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> notFoundException(IOException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpRespose(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}
	
	private ResponseEntity<HttpResponse> createHttpRespose(HttpStatus status, String msg) {
		return new ResponseEntity<>(
				new HttpResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), msg.toUpperCase()),
				status);
	}
}
