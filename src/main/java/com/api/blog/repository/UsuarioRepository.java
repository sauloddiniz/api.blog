package com.api.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blog.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByNome(String nome);

	Usuario findByEmail(String email);
}
