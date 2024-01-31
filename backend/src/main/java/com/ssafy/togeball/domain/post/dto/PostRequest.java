package com.ssafy.togeball.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class PostRequest {

    @JsonProperty
    private User user;

    private String title;

    @JsonProperty
    private String content;

    public static Post toEntity(PostRequest postRequest) {
        return Post.builder()
                .user(postRequest.getUser())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
    }
}
