package com.ssafy.togeball.domain.matching.entity;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
class MatchingEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    private MatchingChatroom matchingChatroom;

    @BeforeEach
    void setup() {
        dataInit();
    }

    private void dataInit() {
        matchingChatroom = MatchingChatroom.builder()
                .title("title")
                .build();
    }

    @Test
    void createAndCascadePersistMatchingChatroomTest() {

        // given
        Matching matching = Matching.builder()
                .matchingChatroom(matchingChatroom)
                .title("title")
                .capacity(10)
                .build();

        entityManager.persist(matching);
        matchingChatroom.setMatching(matching);
        entityManager.flush();
        entityManager.clear();

        // when
        Matching findMatching = entityManager.find(Matching.class, matching.getId());

        // then
        assertNotNull(findMatching);
        assertEquals(matching.getTitle(), findMatching.getTitle());
        assertEquals(matching.getCapacity(), findMatching.getCapacity());
        assertEquals(matchingChatroom.getTitle(),
                findMatching.getMatchingChatroom().getTitle());
        assertEquals(matchingChatroom.getMatching().getId(),
                matching.getId());
    }
}
