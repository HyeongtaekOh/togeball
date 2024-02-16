package com.ssafy.togeball.domain.post.dto;

import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private int userId;

    private String title;

    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
