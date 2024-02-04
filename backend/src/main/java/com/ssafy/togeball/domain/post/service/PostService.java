package com.ssafy.togeball.domain.post.service;

import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.post.dto.PostRequest;
import com.ssafy.togeball.domain.post.dto.PostResponse;
import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.post.exception.PostErrorCode;
import com.ssafy.togeball.domain.post.exception.PostNotFoundException;
import com.ssafy.togeball.domain.post.repository.PostRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public Page<PostResponse> showAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponse::of);
    }

    @Transactional
    public PostResponse showPost(int postId) {
        Post post =  postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return PostResponse.of(post);
    }

    @Transactional
    public int writePost(PostRequest postRequest) {
        User user = userRepository.findById(postRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        Post savedPost = postRepository.save(postRequest.toEntity(user));
        return savedPost.getId();
    }

    @Transactional
    public Page<PostResponse> searchByCondition(String key, String word, Pageable pageable) {
        Page<Post> posts;
        switch (key) {
            default:
                posts = postRepository.findByTitleContaining(word, pageable);
                break;
            case "content":
                posts = postRepository.findByContentContaining(word, pageable);
                break;
            case "nickname":
                posts = postRepository.findByUserNicknameContaining(word, pageable);
                break;
            case "userId":
                posts = postRepository.findByUserId(Integer.parseInt(word), pageable);
                break;
        }
        return posts.map(PostResponse::of);
    }

    @Transactional
    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        postRepository.deleteById(postId);
    }
}
