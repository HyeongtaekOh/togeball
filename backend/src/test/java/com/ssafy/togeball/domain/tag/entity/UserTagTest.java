package com.ssafy.togeball.domain.tag.entity;

import com.ssafy.togeball.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class UserTagTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void userTagLazyLoadingTest() {

        User user = User.builder()
                .email("test@gmail.com")
                .nickname("nickname")
                .build();

        Tag tag = Tag.builder()
                .content("INFP")
                .type(TagType.MBTI)
                .build();

        UserTag userTag = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();

        entityManager.persist(user);
        entityManager.persist(tag);
        entityManager.persist(userTag);
        entityManager.flush();

        entityManager.clear();

        UserTag found = entityManager.find(UserTag.class, userTag.getId());

        assertFalse(Hibernate.isInitialized(found.getTag()));
        assertFalse(Hibernate.isInitialized(found.getUser()));
        assertDoesNotThrow(() -> {
            User foundUser = found.getUser();
            log.info("user email : {}", foundUser.getEmail());

            Tag foundTag = found.getTag();
            log.info("tag content : {}", foundTag.getContent());
        });
    }

    // User - UserTag - Tag 지연로딩 테스트
    @Test
    void userAndTagLazyLoadingTest() {

        User user = User.builder()
                .email("test@gmail.com")
                .nickname("nickname")
                .build();

        Tag tag = Tag.builder()
                .content("INFP")
                .type(TagType.MBTI)
                .build();

        UserTag userTag = UserTag.builder()
                .tag(tag)
                .user(user)
                .build();

        Integer userId = entityManager.persist(user).getId();
        Integer tagId = entityManager.persist(tag).getId();
        entityManager.persist(userTag);
        entityManager.flush();

        entityManager.clear();

        User foundUser = entityManager.find(User.class, userId);
        assertNotNull(foundUser);
        System.out.println("user email : " + foundUser.getEmail());

        log.info("User 로딩, Usertags 로딩 전");
        assertFalse(Hibernate.isInitialized(foundUser.getUserTags()));


        List<UserTag> userTagsInUser = foundUser.getUserTags();
        log.info("User 로딩, Usertags 로딩 후");
        for (UserTag ut : userTagsInUser) {
            assertFalse(Hibernate.isInitialized(ut.getTag()));
        }

        entityManager.clear();

        Tag foundTag = entityManager.find(Tag.class, tagId);
        assertNotNull(foundTag);
        System.out.println("tag content : " + foundTag.getContent());

        log.info("Tag 로딩, Usertags 로딩 전");
        assertFalse(Hibernate.isInitialized(foundTag.getUserTags()));

        List<UserTag> userTagsInTag = foundTag.getUserTags();
        log.info("Tag 로딩, Usertags 로딩 후");
        for (UserTag ut : userTagsInTag) {
            assertFalse(Hibernate.isInitialized(ut.getUser()));
        }
    }
}
