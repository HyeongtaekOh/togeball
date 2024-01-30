package com.ssafy.togeball.domain.post.service;

import com.ssafy.togeball.domain.post.dto.PostResponse;
import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> findByCondition(String key, String word) {
        List<Post> posts;
        switch(key) {
            case "title":
                posts = postRepository.findByTitleContaining(word);
                break;
            case "content":
                posts = postRepository.findByContentContaining(word);
                break;
            case "user":
                posts = postRepository.findByUserNicknameContaining(word);
                break;
            case "userId":
                posts = postRepository.findByUserId(Integer.parseInt(word));
                break;
            default:
                posts = new ArrayList<>();
                break;
        }
        return posts.stream()
                .map(PostResponse::of)
                .toList();
    }
}
