package com.ssafy.togeball.domain.post.controller;

import com.ssafy.togeball.domain.post.dto.PostResponse;
import com.ssafy.togeball.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //TODO: 게시물 전체조회, 상세조회, 작성, 수정, 삭제, 페이지네이션 관련

    @GetMapping
    public ResponseEntity<?> searchByCondition(@RequestParam(name="key") String key, @RequestParam(name="value") String value) {
        List<PostResponse> posts = postService.findByCondition(key, value);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
