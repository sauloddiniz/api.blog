package com.api.blog.enums;

import static com.api.blog.constant.Authority.USUARIO_AUTHORITIES;
import static com.api.blog.constant.Authority.ADMIN_AUTHORITIES;;

public enum Role {
	
	ROLE_USER(USUARIO_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES);

	private String[] authorities;
	
	Role(String...authorities) {
		this.authorities = authorities;
	}
	
	public String[] getAuthorities() {
		return authorities;
	}
	
}
