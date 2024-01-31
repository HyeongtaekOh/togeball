package com.ssafy.togeball.domain.post.controller;

import com.ssafy.togeball.domain.post.dto.PostRequest;
import com.ssafy.togeball.domain.post.dto.PostResponse;
import com.ssafy.togeball.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //TODO: 게시물 전체조회, 상세조회, 작성, 수정, 삭제, 페이지네이션 관련

    @GetMapping
    public ResponseEntity<?> showAllPosts(@RequestParam(name="pageNumber", required=false, defaultValue="0") int pageNumber, //페이지 번호: 0부터 시작
                                         @RequestParam(name="pageSize", required=false, defaultValue="10") int pageSize) { //페이지 사이즈: 보여줄 게시물 개수)
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostResponse> posts = postService.showAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> showPost(@PathVariable(name="postId") int postId) {
        PostResponse post = postService.showPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> writePost(@RequestBody PostRequest postRequest) {
        int postId = postService.writePost(postRequest);
        return new ResponseEntity<>(postId, HttpStatus.CREATED); //작성된 게시물 번호
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByCondition(@RequestParam(name="key") String key, //title, content, nickname, userId
                                               @RequestParam(name="value", required=false) String value,
                                               @RequestParam(name="pageNumber", required=false, defaultValue="0") int pageNumber, //페이지 번호: 0부터 시작
                                               @RequestParam(name="pageSize", required=false, defaultValue="10") int pageSize) { //페이지 사이즈: 보여줄 게시물 개수
        if (value==null || value.trim().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostResponse> posts = postService.searchByCondition(key, value, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
