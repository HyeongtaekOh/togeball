package com.ssafy.togeball.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.togeball.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PostResponse {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String userNickname;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    @JsonProperty
    private LocalDateTime regdate;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .regdate(post.getRegdate())
                .build();
    }
}
