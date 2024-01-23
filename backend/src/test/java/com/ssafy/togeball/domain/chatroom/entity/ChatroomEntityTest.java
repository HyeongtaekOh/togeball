package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
public class ChatroomEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void recruitChatroomLazyLoadingTest() { // User 객체를 Lazy Loading으로 가져오는지 확인

        // 테스트 데이터 저장
        User manager = User.builder()
                .email("test@gmail.com")
                .password("password")
                .nickname("nickname")
                .build();
        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();
        recruitChatroom.setManager(manager);
        entityManager.persist(manager);
        entityManager.persist(recruitChatroom);
        entityManager.flush();

        // 영속성 컨텍스트 초기화
        entityManager.clear();

        // 엔티티 조회
        RecruitChatroom found = (RecruitChatroom) entityManager.find(Chatroom.class, recruitChatroom.getId());

        // 지연 로딩 검증
        assertFalse(Hibernate.isInitialized(found.getManager()));

        // 지연 로딩 동작 확인
        assertDoesNotThrow(() -> {
            User user = found.getManager();
            log.info("user email: {}", user.getEmail());
        });
    }

    @Test
    void saveCascadeTest() {

        // given
        User manager = User.builder()
                .email("test@test.com")
                .nickname("nickname")
                .password("password")
                .build();

        User user1 = User.builder()
                .email("test2@test.com")
                .nickname("nickname2")
                .password("password")
                .build();
        User user2 = User.builder()
                .email("test3@test.com")
                .nickname("nickname3")
                .password("password")
                .build();

        Tag tag1 = Tag.builder()
                .type(TagType.CHEERING_STYLE)
                .content("tag1")
                .build();
        Tag tag2 = Tag.builder()
                .type(TagType.PREFERRED_SEAT)
                .content("tag2")
                .build();
        Tag tag3 = Tag.builder()
                .type(TagType.PREFERRED_STADIUM)
                .content("tag3")
                .build();

        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .manager(manager)
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();

        ChatroomMembership chatroomMembership1 = ChatroomMembership.createChatroomMembership(recruitChatroom, user1);
        ChatroomMembership chatroomMembership2 = ChatroomMembership.createChatroomMembership(recruitChatroom, user2);

        RecruitTag recruitTag1 = RecruitTag.createRecruitTag(recruitChatroom, tag1);
        RecruitTag recruitTag2 = RecruitTag.createRecruitTag(recruitChatroom, tag2);
        RecruitTag recruitTag3 = RecruitTag.createRecruitTag(recruitChatroom, tag3);

        // when
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(tag1);
        entityManager.persist(tag2);
        entityManager.persist(tag3);
        entityManager.persist(recruitChatroom);
        entityManager.flush();
        entityManager.clear();

        // then
        RecruitChatroom found = (RecruitChatroom) entityManager.find(Chatroom.class, recruitChatroom.getId());
        log.info("지연 로딩 전");
        assertAll(
                () -> assertEquals(manager.getId(), found.getManager().getId()),
                () -> assertEquals(2, found.getChatroomMemberships().size()),
                () -> assertEquals(3, found.getRecruitTags().size())
        );

    }
}
