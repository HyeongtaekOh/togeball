package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
}
