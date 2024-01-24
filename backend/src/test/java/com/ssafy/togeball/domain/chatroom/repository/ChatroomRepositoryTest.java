package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.entity.Stadium;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@Slf4j
@DataJpaTest
class ChatroomRepositoryTest {
    /**
     * 미작성 테스트 목록
     * - Chatroom Soft Delete 테스트
     * - RecruitChatroom save 시 User, Tag cascade 테스트
     */

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private User manager;
    private Game game;
    private Club homeClub;
    private Club awayClub;
    private Stadium stadium;
    private Matching matching;

    @BeforeEach
    void setup() {
        dataInit();
    }

    private void dataInit() {
        manager = User.builder()
                .email("test@gmail.com")
                .password("password")
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
        matching = Matching.builder()
                .build();
        entityManager.persist(manager);
        entityManager.persist(homeClub);
        entityManager.persist(awayClub);
        entityManager.persist(stadium);
        entityManager.persist(game);
        entityManager.persist(matching);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void saveGameChatroom() {

        // given
        GameChatroom gameChatroom = GameChatroom.builder()
                .title("game chatroom")
                .game(game)
                .build();

        // when
        chatroomRepository.save(gameChatroom);
        Chatroom saved = chatroomRepository.findById(gameChatroom.getId()).orElse(null);


        // then
        log.info("saved: id = {}, title = {}", gameChatroom.getId(), gameChatroom.getTitle());
        assertEquals(gameChatroom.getTitle(), saved.getTitle());
        assertInstanceOf(GameChatroom.class, saved);
    }

    @Test
    void saveRecruitChatroom() {

        // given
        RecruitChatroom recruitChatroom1 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .build();
        RecruitChatroom recruitChatroom2 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(awayClub)
                .title("recruit chatroom 2")
                .description("description")
                .capacity(10)
                .build();

        // when
        RecruitChatroom saved1 = chatroomRepository.save(recruitChatroom1);
        RecruitChatroom saved2 = chatroomRepository.save(recruitChatroom2);

        // then
        log.info("saved1: id = {}, title = {}, description = {}, capacity = {}", recruitChatroom1.getId(), recruitChatroom1.getTitle(), recruitChatroom1.getDescription(), recruitChatroom1.getCapacity());
        assertEquals(recruitChatroom1.getManager().getEmail(), saved1.getManager().getEmail());
        assertEquals(recruitChatroom1.getTitle(), saved1.getTitle());
        assertEquals(recruitChatroom1.getDescription(), saved1.getDescription());
        assertEquals(recruitChatroom1.getCapacity(), saved1.getCapacity());
        assertInstanceOf(RecruitChatroom.class, saved1);

        log.info("saved2: id = {}, title = {}, description = {}, capacity = {}", recruitChatroom2.getId(), recruitChatroom2.getTitle(), recruitChatroom2.getDescription(), recruitChatroom2.getCapacity());
        assertEquals(recruitChatroom2.getManager().getEmail(), saved2.getManager().getEmail());
        assertEquals(recruitChatroom2.getTitle(), saved2.getTitle());
        assertEquals(recruitChatroom2.getDescription(), saved2.getDescription());
        assertEquals(recruitChatroom2.getCapacity(), saved2.getCapacity());
        assertInstanceOf(RecruitChatroom.class, saved2);
    }

    @Test
    void findById() {

        // given
        GameChatroom gameChatroom1 = GameChatroom.builder()
                .title("game chatroom 1")
                .game(game)
                .build();

        // when
        Chatroom saved1 = chatroomRepository.save(gameChatroom1);
        Chatroom found1 = chatroomRepository.findById(saved1.getId()).orElse(null);

        // then
        assertInstanceOf(GameChatroom.class, found1);
        assertEquals(gameChatroom1.getTitle(), found1.getTitle());
    }

    // GameChatroom, RecruitChatroom, MatchingChatroom 3개의 채팅방을 저장하고 findAll()로 모두 조회
    @Test
    void findAll() {

        // given
        GameChatroom gameChatroom = GameChatroom.builder()
                .title("game chatroom")
                .game(game)
                .build();
        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title("matching chatroom")
                .matching(matching)
                .build();

        // when
        chatroomRepository.save(gameChatroom);
        chatroomRepository.save(recruitChatroom);
        chatroomRepository.save(matchingChatroom);
        List<Chatroom> chatrooms = chatroomRepository.findAll();

        // then

        // 3개의 채팅방이 저장되었는지 확인
        assertEquals(3, chatrooms.size());

        // 각 채팅방의 타입이 맞는지 확인
        chatrooms.forEach(chatroom -> {
            if (chatroom instanceof GameChatroom) {
                assertEquals(gameChatroom.getTitle(), chatroom.getTitle());
            } else if (chatroom instanceof RecruitChatroom) {
                assertEquals(recruitChatroom.getTitle(), chatroom.getTitle());
                assertEquals(recruitChatroom.getDescription(), ((RecruitChatroom) chatroom).getDescription());
                assertEquals(recruitChatroom.getCapacity(), ((RecruitChatroom) chatroom).getCapacity());
            } else if (chatroom instanceof MatchingChatroom) {
                assertEquals(matchingChatroom.getTitle(), chatroom.getTitle());
            }
        });
    }

    @Test
    void findByTagIds() {

        // given
        RecruitChatroom recruitChatroom1 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .build();

        Tag tag1 = Tag.builder()
                .content("SSG랜더스필드")
                .type(TagType.PREFERRED_STADIUM)
                .build();
        Tag tag2 = Tag.builder()
                .content("응원지정석")
                .type(TagType.PREFERRED_SEAT)
                .build();
        Tag tag3 = Tag.builder()
                .content("SSG랜더스")
                .type(TagType.PREFERRED_TEAM)
                .build();

        recruitChatroom1.addTag(tag1);
        recruitChatroom1.addTag(tag2);
        recruitChatroom1.addTag(tag3);

        // when
        userRepository.save(manager);
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        chatroomRepository.save(recruitChatroom1);

        List<RecruitChatroom> found1 = chatroomRepository.findByTagIds(List.of(tag1.getId(), tag2.getId()));

        // then
        log.info("found1: {}", found1);
        assertEquals(1, found1.size());
        assertEquals(recruitChatroom1.getTitle(), found1.get(0).getTitle());
    }

    @Test
    void findByTitleContaining() {

        // given
        RecruitChatroom recruitChatroom1 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .build();
        RecruitChatroom recruitChatroom2 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 2")
                .description("description")
                .capacity(10)
                .build();
        RecruitChatroom recruitChatroom3 = RecruitChatroom.builder()
                .manager(manager)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 3")
                .description("description")
                .capacity(10)
                .build();
        GameChatroom gameChatroom1 = GameChatroom.builder()
                .title("game chatroom 1")
                .game(game)
                .build();

        // when
        chatroomRepository.save(recruitChatroom1);
        chatroomRepository.save(recruitChatroom2);
        chatroomRepository.save(recruitChatroom3);
        chatroomRepository.save(gameChatroom1);
        List<Chatroom> found = chatroomRepository.findAllByTitleContaining("chat");

        // then
        assertEquals(4, found.size());
    }
}
