package com.ssafy.togeball.domain.post.repository;

import com.ssafy.togeball.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
