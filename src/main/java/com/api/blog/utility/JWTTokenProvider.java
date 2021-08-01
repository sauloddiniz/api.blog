package com.api.blog.utility;

import static com.api.blog.constant.SecurityConstant.AUTHORITIES;
import static com.api.blog.constant.SecurityConstant.EXPIRATION_TIME;
import static com.api.blog.constant.SecurityConstant.GET_ARRAYS_ADMINISTRATION;
import static com.api.blog.constant.SecurityConstant.GET_ARRAYS_LLC;
import static com.api.blog.constant.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;
import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.api.blog.domain.UserDetail;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	public String geraJwtToken(UserDetail userDetail) {
		String[] claims = obterClaimsDoUsuario(userDetail);
		return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(GET_ARRAYS_ADMINISTRATION).withIssuedAt(new Date())
				.withSubject(userDetail.getUsername()).withArrayClaim(AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(secret.getBytes()));
	}

	public List<GrantedAuthority> obterAuthorities(String token) {
		String[] claims = obterClaimsDoToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public Authentication obterAuthentication(String nomeUsuario, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				nomeUsuario, null, authorities);
		passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return passwordAuthenticationToken;
	}

	public boolean isTokenValid(String nomeUsuario, String token) {
		JWTVerifier verifier = obterValidadeJWTVerifier();
		return StringUtils.isNotEmpty(nomeUsuario) && !isTokenExpired(verifier, token);
	}

	public String obterSubject(String token) {
		JWTVerifier verifier = obterValidadeJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date dataToken = verifier.verify(token).getExpiresAt();
		return dataToken.before(new Date());
	}

	private String[] obterClaimsDoToken(String token) {
		JWTVerifier verifier = obterValidadeJWTVerifier();
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier obterValidadeJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}

	private String[] obterClaimsDoUsuario(UserDetail userDetail) {
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority authority : userDetail.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
}
