package com.ssafy.togeball.domain.post.repository;

import com.ssafy.togeball.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer userId);

    // 특정 단어가 게시물 제목 필드에 포함된 게시물을 검색
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> findByTitleLike(@Param("keyword") String keyword);

    // 특정 단어가 게시물 내용 필드에 포함된 게시물을 검색
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
    List<Post> findByContentLike(@Param("keyword") String keyword);
}
