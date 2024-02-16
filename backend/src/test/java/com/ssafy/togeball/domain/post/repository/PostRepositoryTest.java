package com.ssafy.togeball.domain.post.repository;

import com.ssafy.togeball.domain.post.entity.Post;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    private Pageable pageable;

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
        pageable = PageRequest.of(0, 10);
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
    void findAllTest() {

        // Given
        dataInit();

        // When
        List<Post> allPosts = postRepository.findAll();

        // Then
        assertEquals(2, allPosts.size());
    }

    @Test
    void findByUserIdTest() {

        // Given
        dataInit();
        User saveUser1 = userRepository.save(user1);

        // When
        List<Post> postsFindByUserId = postRepository.findByUserId(saveUser1.getId(), pageable).getContent();

        // Then
        assertEquals(1, postsFindByUserId.size());
    }
    @Test
    void findByUserNicknameContaining() {

        // Given
        dataInit();

        // When
        List<Post> postsFindByUserNicknameContaining
                = postRepository.findByUserNicknameContaining("유경", pageable).getContent();

        // Then
        assertEquals(1, postsFindByUserNicknameContaining.size());
    }

    @Test
    void findByTitleContaining() {

        // Given
        dataInit();

        // When
        List<Post> postsFindByTitleContaining
                = postRepository.findByTitleContaining("유경", pageable).getContent();

        // Then
        assertEquals(1, postsFindByTitleContaining.size());
    }

    @Test
    void findByContentContaining() {

        // Given
        dataInit();

        // When
        List<Post> postsFindByContentContaining
                = postRepository.findByContentContaining("유경", pageable).getContent();

        // Then
        assertEquals(1, postsFindByContentContaining.size());
    }
}
