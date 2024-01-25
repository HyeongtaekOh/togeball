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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
     * - 전체 모집 채팅방 조회
     * - 모집 채팅 등록
     * - 채팅방 정보 수정
     * - 채팅방 삭제
     * - 사용자가 모집중인 채팅방 목록 조회
     */

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatroomMembershipRepository chatroomMembershipRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private User manager;
    private User user1;
    private User user2;
    private User user3;
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
        user1 = User.builder()
                .email("user1@test.com")
                .password("user1")
                .nickname("user1")
                .build();
        user2 = User.builder()
                .email("user2@test.com")
                .password("user2")
                .nickname("user2")
                .build();
        user3 = User.builder()
                .email("user3@test.com")
                .password("user3")
                .nickname("user3")
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
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);
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

        Pageable pageable = PageRequest.of(0, 10);
        List<RecruitChatroom> found1 = chatroomRepository.findByTagIds(List.of(tag1.getId(), tag2.getId()), pageable).getContent();

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

        Pageable pageable = PageRequest.of(0, 10);
        List<Chatroom> found = chatroomRepository.findAllByTitleContaining("chat", pageable).getContent();

        // then
        assertEquals(4, found.size());
    }

    @Test
    void findAllChatroomsByUserId() {

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
        GameChatroom gameChatroom1 = GameChatroom.builder()
                .title("game chatroom 1")
                .game(game)
                .build();
        MatchingChatroom matchingChatroom1 = MatchingChatroom.builder()
                .title("matching chatroom 1")
                .matching(matching)
                .build();

        // when
        chatroomRepository.save(recruitChatroom1);
        chatroomRepository.save(recruitChatroom2);
        chatroomRepository.save(gameChatroom1);
        chatroomRepository.save(matchingChatroom1);
        chatroomRepository.addAllUsers(recruitChatroom1.getId(), List.of(user1.getId(), user2.getId()));
        chatroomRepository.addAllUsers(recruitChatroom2.getId(), List.of(user1.getId(), user3.getId()));
        chatroomRepository.addAllUsers(gameChatroom1.getId(), List.of(user1.getId(), user2.getId(), user3.getId()));
        chatroomRepository.addAllUsers(matchingChatroom1.getId(), List.of(user1.getId(), user2.getId(), user3.getId()));

        Pageable pageable = PageRequest.of(0, 10);

        List<Chatroom> found1 = chatroomRepository.findAllChatroomsByUserId(user1.getId(), pageable).getContent();
        List<Chatroom> found2 = chatroomRepository.findAllChatroomsByUserId(user2.getId(), pageable).getContent();

        // then
        assertEquals(4, found1.size());
        assertEquals(3, found2.size());
    }

    @Test
    void findAllByType() {

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
        GameChatroom gameChatroom1 = GameChatroom.builder()
                .title("game chatroom 1")
                .game(game)
                .build();
        MatchingChatroom matchingChatroom1 = MatchingChatroom.builder()
                .title("matching chatroom 1")
                .matching(matching)
                .build();

        // when
        chatroomRepository.save(recruitChatroom1);
        chatroomRepository.save(recruitChatroom2);
        chatroomRepository.save(gameChatroom1);
        chatroomRepository.save(matchingChatroom1);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Chatroom> found1 = chatroomRepository.findAllByType("RECRUIT", pageable);
        Page<Chatroom> found2 = chatroomRepository.findAllByType("GAME", pageable);
        Page<Chatroom> found3 = chatroomRepository.findAllByType("MATCHING", pageable);

        // then
        log.info("found1: {}", found1);
        log.info("found2: {}", found2);
        log.info("found3: {}", found3);
        assertEquals(2, found1.getContent().size());
        assertEquals(1, found2.getContent().size());
        assertEquals(1, found3.getContent().size());
    }

    @Test
    void findAllRecruitChatroomsByManagerId() {

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
                .manager(user1)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 3")
                .description("description")
                .capacity(10)
                .build();
        RecruitChatroom recruitChatroom4 = RecruitChatroom.builder()
                .manager(user2)
                .game(game)
                .cheeringClub(homeClub)
                .title("recruit chatroom 4")
                .description("description")
                .capacity(10)
                .build();

        // when
        chatroomRepository.save(recruitChatroom1);
        chatroomRepository.save(recruitChatroom2);
        chatroomRepository.save(recruitChatroom3);
        chatroomRepository.save(recruitChatroom4);
        Pageable pageable = PageRequest.of(0, 10);

        Page<RecruitChatroom> found1 = chatroomRepository.findAllRecruitChatroomsByManagerId(manager.getId(), pageable);
        Page<RecruitChatroom> found2 = chatroomRepository.findAllRecruitChatroomsByManagerId(user1.getId(), pageable);
        Page<RecruitChatroom> found3 = chatroomRepository.findAllRecruitChatroomsByManagerId(user2.getId(), pageable);

        // then
        log.info("found1 result: {}", found1.getContent());
        log.info("found2 result: {}", found2.getContent());
        log.info("found3 result: {}", found3.getContent());
        assertEquals(2, found1.getContent().size());
        assertEquals(1, found2.getContent().size());
        assertEquals(1, found3.getContent().size());
    }
}
