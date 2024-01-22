package com.ssafy.togeball.domain.post.entity;

import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PostTest {

    @Test
    void postBuilderTest() {
        //Given
        User user = new User("aycho3030@gmail.com","1234","아영", Gender.FEMALE,
                LocalDateTime.of(1994,4,30,0,0,0),"010-1000-2000","profile1.png");
        String title = "게시물 제목 테스트입니다.";
        String content = "게시물 내용 테스트입니다.";

        //When
        Post post = Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();

        //Then
        System.out.println(post.toString());
        System.out.println("작성자 : " + post.getUser().getNickname());
    }
}
