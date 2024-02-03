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

    private User user1;

    private User user2;

    private Post post1;

    private Post post2;

    void dataInit() {

        user1 = User.builder()
                .email("ykyang@ssafy.com")
                .nickname("유경")
                .profileImage("ykprofile.png")
                .build();
        userRepository.save(user1);
        post1 = Post.builder()
                .user(user1)
                .title("유경의 게시물 제목입니다.")
                .content("유경의 게시물 내용입니다.")
                .build();
        postRepository.save(post1);
        user2 = User.builder()
                .email("htoh@ssafy.com")
                .nickname("형택")
                .profileImage("htprofile.png")
                .build();
        userRepository.save(user2);
        post2 = Post.builder()
                .user(user2)
                .title("형택의 게시물 제목입니다.")
                .content("형택의 게시물 내용입니다.")
                .build();
        postRepository.save(post2);
    }

    @Test
    void postSaveTest() {

        LocalDate now = LocalDate.now();
        User user = User.builder()
                .email("aycho3030@gmail.com")
                .nickname("아영")
                .gender(Gender.FEMALE)
                .birthdate(LocalDateTime.of(1994,4,30,0,0,0))
                .phone("010-1000-2000")
                .profileImage("profile1.png")
                .build();

        User savedUser = userRepository.save(user);

        String title = "저녁 뭐 먹지";
        String content = "메뉴 추천 부탁요";
        Post post = Post.builder()
                .user(savedUser)
                .title(title)
                .content(content)
                .build();
        Post saved = postRepository.save(post);

        assertNotNull(saved.getUser().getId());
        assertEquals("저녁 뭐 먹지", saved.getTitle());
        assertEquals("메뉴 추천 부탁요", saved.getContent());
        assertEquals(now, saved.getRegdate().toLocalDate());
    }

    @Test
    void postFindAllTest() {

    }

    @Test
    void postFindByUserIdTest() {

    }
    @Test
    void postFindByUserNicknameContaining() {

    }

    @Test
    void postFindByTitleContaining() {

    }

    @Test
    void postFindByContentContaining() {

    }
}
