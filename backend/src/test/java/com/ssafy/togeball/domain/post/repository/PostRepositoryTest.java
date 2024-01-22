package com.ssafy.togeball.domain.post.repository;

import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void postCreateTest() {

        LocalDate now = LocalDate.now();
        User user = new User("aycho3030@gmail.com","1234","아영", Gender.FEMALE,
                LocalDateTime.of(1994,4,30,0,0,0),"010-1000-2000","profile1.png");

        User savedUser = userRepository.save(user);
        String title = "게시물 제목 테스트입니다.";
        String content = "게시물 내용 테스트입니다.";

        Post post = Post.builder()
                .user(savedUser)
                .title(title)
                .content(content)
                .build();

        Post saved = postRepository.save(post);

        assertNotNull(saved.getUser().getId());
        assertEquals("게시물 제목 테스트입니다.", saved.getTitle());
        assertEquals("게시물 내용 테스트입니다.", saved.getContent());
        assertEquals(now, saved.getRegDate().toLocalDate());
    }
}
