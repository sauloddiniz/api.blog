package com.api.blog.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	@Column(nullable = false, updatable = false, unique = true)
	private String nome;
	private String senha;
	private String email;
	private String profileImagemUrl;
	private Date dtUltimoLogin;
	private Date dtCriacao;
	private String role; // ROLE_USER{read, edit}, ROLE_ADMIN {delete}
	private String[] authorities;
	private boolean isAtivo;
	private boolean isNaoBloqueado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImagemUrl() {
		return profileImagemUrl;
	}

	public void setProfileImagemUrl(String profileImagemUrl) {
		this.profileImagemUrl = profileImagemUrl;
	}

	public Date getDtUltimoLogin() {
		return dtUltimoLogin;
	}

	public void setDtUltimoLogin(Date dtUltimoLogin) {
		this.dtUltimoLogin = dtUltimoLogin;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

	public boolean isNaoBloqueado() {
		return isNaoBloqueado;
	}

	public void setNaoBloqueado(boolean isNaoBloqueado) {
		this.isNaoBloqueado = isNaoBloqueado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario() {

	}

	public Usuario(Long id, String nome, String senha, String email, String profileImagemUrl, Date dtUltimoLogin,
			Date dtCriacao, String role, String[] authorities, boolean isAtivo, boolean isNaoBloqueado) {
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.profileImagemUrl = profileImagemUrl;
		this.dtUltimoLogin = dtUltimoLogin;
		this.dtCriacao = dtCriacao;
		this.role = role;
		this.authorities = authorities;
		this.isAtivo = isAtivo;
		this.isNaoBloqueado = isNaoBloqueado;
	}

}
