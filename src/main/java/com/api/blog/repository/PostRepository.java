package com.api.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllByUsuarioId(Long idUsuario);

}
