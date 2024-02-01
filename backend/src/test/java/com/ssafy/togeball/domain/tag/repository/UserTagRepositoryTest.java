package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.tag.entity.UserTag;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTagRepositoryTest {

    @Autowired
    private UserTagRepository userTagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private User user;

    private Tag tag;

    @BeforeEach
    void setup() {
        dataInit();
    }

    @Test
    void userTagSaveTest() {

        UserTag userTag = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();
        UserTag saved = userTagRepository.save(userTag);

        assertNotNull(saved.getId());

        assertEquals("test@gmail.com", saved.getUser().getEmail());
        assertEquals("INFP", saved.getTag().getContent());
    }

    @Test
    void userTagDuplicateTest() {

        UserTag userTag1 = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();
        userTagRepository.save(userTag1);

        UserTag userTag2 = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> userTagRepository.save(userTag2));
    }

    @Test
    void findByTagIdAndUserIdTest() {

        UserTag userTag = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();
        userTagRepository.save(userTag);

        assertTrue(userTagRepository.findByTagIdAndUserId(tag.getId(), user.getId()).isPresent());
        assertFalse(userTagRepository.findByTagIdAndUserId(tag.getId(), -1).isPresent());
    }

    @Test
    void findByUserIdTest() {

        UserTag userTag1 = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();
        userTagRepository.save(userTag1);

        Tag tag2 = Tag.builder()
                .content("응원가형")
                .type(TagType.CHEERING_STYLE)
                .build();
        tagRepository.save(tag2);

        UserTag userTag2 = UserTag.builder()
                .tag(tag2)
                .user(user)
                .build();
        userTagRepository.save(userTag2);

        User user2 = User.builder()
                .email("test2@gmail.com")
                .nickname("nickname2")
                .build();
        userRepository.save(user2);

        UserTag userTag3 = UserTag.builder()
                .tag(tag2)
                .user(user2)
                .build();
        userTagRepository.save(userTag3);

        Set<UserTag> found = userTagRepository.findByUserId(user.getId());
        assertEquals(2, found.size());
    }

    @Test
    void findByTagIdTest() {

        UserTag userTag1 = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();
        userTagRepository.save(userTag1);

        Tag tag2 = Tag.builder()
                .content("응원가형")
                .type(TagType.CHEERING_STYLE)
                .build();
        tagRepository.save(tag2);

        UserTag userTag2 = UserTag.builder()
                .tag(tag2)
                .user(user)
                .build();
        userTagRepository.save(userTag2);

        User user2 = User.builder()
                .email("test2@gmail.com")
                .nickname("nickname2")
                .build();
        userRepository.save(user2);

        UserTag userTag3 = UserTag.builder()
                .tag(tag)
                .user(user2)
                .build();
        userTagRepository.save(userTag3);

        Set<UserTag> found = userTagRepository.findByTagId(tag.getId());
        assertEquals(2, found.size());
    }

    void dataInit() {

        user = User.builder()
                .email("test@gmail.com")
                .nickname("nickname")
                .build();
        userRepository.save(user);

        tag = Tag.builder()
                .content("INFP")
                .type(TagType.MBTI)
                .build();
        tagRepository.save(tag);
    }
}
