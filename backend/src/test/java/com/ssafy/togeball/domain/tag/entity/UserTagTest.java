package com.ssafy.togeball.domain.tag.entity;

import com.ssafy.togeball.domain.user.entity.User;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTagTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void userTagLazyLoadingTest() {

        User user = User.builder()
                .email("test@gmail.com")
                .password("password")
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
            System.out.println("user email : " + foundUser.getEmail());

            Tag foundTag = found.getTag();
            System.out.println("tag content : " + foundTag.getContent());
        });
    }
}
