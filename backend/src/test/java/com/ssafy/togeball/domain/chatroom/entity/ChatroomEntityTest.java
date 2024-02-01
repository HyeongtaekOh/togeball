package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.entity.Stadium;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class ChatroomEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    private User manager;
    private Game game;
    private Club homeClub;
    private Club awayClub;
    private Stadium stadium;

    @BeforeEach
    void setup() {
        dataInit();
    }

    private void dataInit() {
        manager = User.builder()
                .email("test@gmail.com")
                .nickname("nickname")
                .build();
        homeClub = Club.builder()
                .clubName("랜더스")
                .sponsorName("SSG")
                .ranking(1)
                .build();
        awayClub = Club.builder()
                .clubName("자이언츠")
                .sponsorName("롯데")
                .ranking(2)
                .build();
        stadium = Stadium.builder()
                .name("문학경기장")
                .fullName("문학경기장")
                .address("서울특별시 동작구 상도로 369")
                .latitude(37.496041)
                .longitude(126.953764)
                .build();
        game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(LocalDateTime.now())
                .build();
        entityManager.persist(manager);
        entityManager.persist(homeClub);
        entityManager.persist(awayClub);
        entityManager.persist(stadium);
        entityManager.persist(game);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void recruitChatroomLazyLoadingTest() { // User 객체를 Lazy Loading으로 가져오는지 확인

        // 테스트 데이터 저장
        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();
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

        User user1 = User.builder()
                .email("test2@test.com")
                .nickname("nickname2")
                .build();
        User user2 = User.builder()
                .email("test3@test.com")
                .nickname("nickname3")
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
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();

        recruitChatroom.addMember(user1);
        recruitChatroom.addMember(user2);

        recruitChatroom.addTag(tag1);
        recruitChatroom.addTag(tag2);
        recruitChatroom.addTag(tag3);

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
