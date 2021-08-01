package com.api.blog.constant;

public class Authority {

	public static final String[] USUARIO_AUTHORITIES = { "user:read", "user:update", "user:create" };
	public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:update", "user:create", "user:delete" };

}
