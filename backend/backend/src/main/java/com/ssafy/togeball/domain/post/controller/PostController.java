package com.ssafy.togeball.domain.post.controller;

import com.ssafy.togeball.domain.post.dto.PostRequest;
import com.ssafy.togeball.domain.post.dto.PostResponse;
import com.ssafy.togeball.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> showAllPosts(Pageable pageable) {
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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(postId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByCondition(@RequestParam(name="key") String key, //title, content, nickname, userId
                                               @RequestParam(name="value", required=false) String value, Pageable pageable) {
        if (value==null || value.trim().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        Page<PostResponse> posts = postService.searchByCondition(key, value, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
