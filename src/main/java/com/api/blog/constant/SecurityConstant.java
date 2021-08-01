package com.api.blog.constant;

public class SecurityConstant {

	public static final long EXPIRATION_TIME = 432000000; // 5 DIAS
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verifieds";
	public static final String GET_ARRAYS_LLC = "Get arrays, LLC";
	public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
	public static final String AUTHORITIES = "Authorities";
	public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You dont at have permission access this page";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = { "/usuario/login", "/usuario/salvar", "/usuario/resetpassword/***",
			"/usuario/image/**" };

}
