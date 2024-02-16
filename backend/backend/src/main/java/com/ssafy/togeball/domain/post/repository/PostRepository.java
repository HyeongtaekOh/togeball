package com.ssafy.togeball.domain.post.repository;

import com.ssafy.togeball.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByUserId(Integer userId, Pageable pageable);

    Page<Post> findByUserNicknameContaining(String userNickname, Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByContentContaining(String content, Pageable pageable);
}
