package com.ssafy.togeball.domain.post.entity;

import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PostTest {

    @Test
    void postBuilderTest() {
        //Given
        User user = User.builder()
                .email("aycho3030@gmail.com")
                .nickname("아영")
                .gender(Gender.FEMALE)
                .birthdate(LocalDateTime.of(1994,4,30,0,0,0))
                .phone("010-1000-2000")
                .profileImage("profile1.png")
                .build();
        String title = "게시물 제목 테스트입니다.";
        String content = "게시물 내용 테스트입니다.";

        //When
        Post post = Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();

        //Then
        System.out.println("User: " + post.getUser().getNickname());
        System.out.println("Title: " + post.getTitle());
        System.out.println("Content: " + post.getContent());
    }
}
