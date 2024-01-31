package com.ssafy.togeball.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @JsonProperty
    private int userId;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
