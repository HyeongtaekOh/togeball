package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {

        User user1 = User.builder()
                .email("aycho@ssafy.com")
                .nickname("아영")
                .profileImage("profile.jpg")
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .email("htoh@ssafy.com")
                .nickname("형택")
                .profileImage("profile.jpg")
                .build();

        userRepository.save(user2);
    }

    @Test
    void userSignUpTest() {

        LocalDate now = LocalDate.now();

        User user = User.builder()
                .email("email@gmail.com")
                .nickname("nickname")
                .profileImage("profile.jpg")
                .build();

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());

        assertEquals("email@gmail.com", saved.getEmail());
        assertEquals("nickname", saved.getNickname());
        assertEquals("profile.jpg", saved.getProfileImage());
        assertEquals(now, saved.getRegdate().toLocalDate());
        assertFalse(saved.isDeleted());
    }

    @Test
    void userDuplicateEmailTest() {

        String email = "email@gmail.com";

        User user1 = User.builder()
                .email(email)
                .nickname("nickname")
                .profileImage("profile.jpg")
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .email(email)
                .nickname("nickname2")
                .profileImage("profile.jpg")
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(user2));
    }

    @Test
    void userDuplicateNicknameTest() {

        String nickname = "nickname";

        User user1 = User.builder()
                .email("email@gmail.com")
                .nickname(nickname)
                .profileImage("profile.jpg")
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .email("email2@gmail.com")
                .nickname(nickname)
                .profileImage("profile.jpg")
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(user2));
    }

    @Test
    void userFindByEmailTest() {

        String email = "aycho@ssafy.com";
        Optional<User> find = userRepository.findByEmail(email);
        assertTrue(find.isPresent());

        String notExistingEmail = "notEmail@ssafy.com";
        Optional<User> notExist = userRepository.findByEmail(notExistingEmail);
        assertTrue(notExist.isEmpty());
    }

    @Test
    void userFindByNicknameTest() {

        String nickname = "형택";
        Optional<User> find = userRepository.findByNickname(nickname);
        assertTrue(find.isPresent());

        String notExistingNickname = "현택";
        Optional<User> notExist = userRepository.findByEmail(notExistingNickname);
        assertTrue(notExist.isEmpty());
    }
}