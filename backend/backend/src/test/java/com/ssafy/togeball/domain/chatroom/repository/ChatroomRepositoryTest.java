package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomSearchCondition;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.entity.Stadium;
import com.ssafy.togeball.domain.league.exception.ClubNotFoundException;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.tag.repository.RecruitTagRepository;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    private RecruitTagRepository recruitTagRepository;

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
                .nickname("nickname")
                .build();
        user1 = User.builder()
                .email("user1@test.com")
                .nickname("user1")
                .build();
        user2 = User.builder()
                .email("user2@test.com")
                .nickname("user2")
                .build();
        user3 = User.builder()
                .email("user3@test.com")
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
    void findUsersByChatroomId() {

            // given
            RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                    .manager(manager)
                    .game(game)
                    .cheeringClub(homeClub)
                    .title("recruit chatroom")
                    .description("description")
                    .capacity(10)
                    .build();
            chatroomRepository.save(recruitChatroom);
            chatroomRepository.addParticipants(recruitChatroom.getId(), List.of(user1.getId(), user2.getId(), user3.getId()));

            // when
            List<User> found = chatroomRepository.findParticipantsByChatroomId(recruitChatroom.getId());
            found.forEach(user -> log.info("user: {}", user.getNickname()));

            // then
            assertEquals(3, found.size());
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
                .capacity(10)
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
        log.info("chatroom type: {}", chatrooms.stream().map(Chatroom::getType).toList());
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
                .capacity(10)
                .build();

        // when
        chatroomRepository.save(recruitChatroom1);
        chatroomRepository.save(recruitChatroom2);
        chatroomRepository.save(gameChatroom1);
        chatroomRepository.save(matchingChatroom1);
        chatroomRepository.addParticipants(recruitChatroom1.getId(), List.of(user1.getId(), user2.getId()));
        chatroomRepository.addParticipants(recruitChatroom2.getId(), List.of(user1.getId(), user3.getId()));
        chatroomRepository.addParticipants(gameChatroom1.getId(), List.of(user1.getId(), user2.getId(), user3.getId()));
        chatroomRepository.addParticipants(matchingChatroom1.getId(), List.of(user1.getId(), user2.getId(), user3.getId()));

        Pageable pageable = PageRequest.of(0, 10);

        List<Chatroom> found1 = chatroomRepository.findChatroomsByUserId(user1.getId(), pageable).getContent();
        List<Chatroom> found2 = chatroomRepository.findChatroomsByUserId(user2.getId(), pageable).getContent();
        List<Chatroom> found3 = chatroomRepository.findChatroomsByUserId(1, pageable).getContent();

        // then
        assertEquals(3, found1.size());
        assertEquals(2, found2.size());
        log.info("found3: {}", found3);
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
                .capacity(10)
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

        Page<RecruitChatroom> found1 = chatroomRepository.findRecruitChatroomsByManagerId(manager.getId(), pageable);
        Page<RecruitChatroom> found2 = chatroomRepository.findRecruitChatroomsByManagerId(user1.getId(), pageable);
        Page<RecruitChatroom> found3 = chatroomRepository.findRecruitChatroomsByManagerId(user2.getId(), pageable);

        // then
        log.info("found1 result: {}", found1.getContent());
        log.info("found2 result: {}", found2.getContent());
        log.info("found3 result: {}", found3.getContent());
        assertEquals(2, found1.getContent().size());
        assertEquals(1, found2.getContent().size());
        assertEquals(1, found3.getContent().size());
    }

    @Test
    void createRecruitChatroom() {

        Tag tag1 = tagRepository.save(Tag.builder().content("SSG랜더스필드").type(TagType.PREFERRED_STADIUM).build());
        Tag tag2 = tagRepository.save(Tag.builder().content("응원지정석").type(TagType.PREFERRED_SEAT).build());
        Tag tag3 = tagRepository.save(Tag.builder().content("SSG랜더스").type(TagType.PREFERRED_TEAM).build());

        RecruitChatroomRequest chatroomDto = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag3.getId()))
                .build();
        log.info("chatroomDto: {}", chatroomDto);
        RecruitChatroom recruitChatroom = chatroomRepository.createRecruitChatroom(chatroomDto);
        assertAll(
                () -> assertEquals(chatroomDto.getManagerId(), recruitChatroom.getManager().getId()),
                () -> assertEquals(chatroomDto.getGameId(), recruitChatroom.getGame().getId()),
                () -> assertEquals(chatroomDto.getCheeringClubId(), recruitChatroom.getCheeringClub().getId()),
                () -> assertEquals(chatroomDto.getTitle(), recruitChatroom.getTitle()),
                () -> assertEquals(chatroomDto.getDescription(), recruitChatroom.getDescription()),
                () -> assertEquals(chatroomDto.getCapacity(), recruitChatroom.getCapacity()),
                () -> assertEquals(3, recruitChatroom.getRecruitTags().size())
        );
    }

    @Test
    void createRecruitChatroomFailed() {

        RecruitChatroomRequest chatroomDto = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(0)
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .build();

        assertThrows(ClubNotFoundException.class, () -> chatroomRepository.createRecruitChatroom(chatroomDto));
    }

    @Test
    void updateRecruitChatroom() {

        // given
        Tag tag1 = tagRepository.save(Tag.builder().content("SSG랜더스필드").type(TagType.PREFERRED_STADIUM).build());
        Tag tag2 = tagRepository.save(Tag.builder().content("응원지정석").type(TagType.PREFERRED_SEAT).build());
        Tag tag3 = tagRepository.save(Tag.builder().content("SSG랜더스").type(TagType.PREFERRED_TEAM).build());
        RecruitChatroomRequest chatroomDto = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId()))
                .build();
        RecruitChatroom recruitChatroom = chatroomRepository.createRecruitChatroom(chatroomDto);

        // when
        log.info("업데이트 전");
        RecruitChatroomRequest updateDto = RecruitChatroomRequest.builder()
                .id(recruitChatroom.getId())
                .managerId(user1.getId())
                .gameId(game.getId())
                .cheeringClubId(awayClub.getId())
                .title("recruit chatroom 2")
                .description("description 2")
                .capacity(15)
                .tagIds(List.of(tag2.getId(), tag3.getId()))
                .build();
        chatroomRepository.updateRecruitChatroom(updateDto);

        // then
        log.info("업데이트 후");
        RecruitChatroom found = (RecruitChatroom) chatroomRepository.findById(recruitChatroom.getId()).orElse(null);
        assertAll(
                () -> assertEquals(user1.getId(), found.getManager().getId()),
                () -> assertEquals(updateDto.getGameId(), found.getGame().getId()),
                () -> assertEquals(updateDto.getCheeringClubId(), found.getCheeringClub().getId()),
                () -> assertEquals(updateDto.getTitle(), found.getTitle()),
                () -> assertEquals(updateDto.getDescription(), found.getDescription()),
                () -> assertEquals(updateDto.getCapacity(), found.getCapacity()),
                () -> assertEquals(2, found.getRecruitTags().size())
        );
        log.info("manager : {}", found.getManager().getNickname());
        log.info("game : {}", found.getGame().getHomeClub().getClubName());
        log.info("cheeringClub : {}", found.getCheeringClub().getClubName());
        log.info("title : {}", found.getTitle());
        log.info("description : {}", found.getDescription());
        log.info("capacity : {}", found.getCapacity());
        log.info("tags : {}", found.getRecruitTags().stream().map(tag -> tag.getTag().getContent()).toList());
    }

    @Test
    void deleteChatroom() {

        // given
        Tag tag1 = tagRepository.save(Tag.builder().content("SSG랜더스필드").type(TagType.PREFERRED_STADIUM).build());
        Tag tag2 = tagRepository.save(Tag.builder().content("응원지정석").type(TagType.PREFERRED_SEAT).build());
        Tag tag3 = tagRepository.save(Tag.builder().content("SSG랜더스").type(TagType.PREFERRED_TEAM).build());
        RecruitChatroomRequest chatroomDto = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag3.getId()))
                .build();
        RecruitChatroom recruitChatroom = chatroomRepository.createRecruitChatroom(chatroomDto);
        RecruitChatroom found = (RecruitChatroom) chatroomRepository.findById(recruitChatroom.getId()).orElse(null);
        assertEquals(3, found.getRecruitTags().size());

        // when
        chatroomRepository.delete(recruitChatroom);

        // then
        assertNull(chatroomRepository.findById(recruitChatroom.getId()).orElse(null));
        assertAll(
                () -> assertNull(chatroomRepository.findById(recruitChatroom.getId()).orElse(null)),
                () -> assertEquals(0, recruitTagRepository.findAllByRecruitChatroomId(recruitChatroom.getId()).size())
        );
    }

    @Test
    void findRecruitChatroomsByCondition() {

        // given
        Tag tag1 = tagRepository.save(Tag.builder().content("SSG랜더스필드").type(TagType.PREFERRED_STADIUM).build());
        Tag tag2 = tagRepository.save(Tag.builder().content("응원지정석").type(TagType.PREFERRED_SEAT).build());
        Tag tag3 = tagRepository.save(Tag.builder().content("SSG랜더스").type(TagType.PREFERRED_TEAM).build());
        Tag tag4 = tagRepository.save(Tag.builder().content("롯데자이언츠").type(TagType.PREFERRED_TEAM).build());
        Tag tag5 = tagRepository.save(Tag.builder().content("문학경기장").type(TagType.PREFERRED_STADIUM).build());
        Tag tag6 = tagRepository.save(Tag.builder().content("외야일반석").type(TagType.PREFERRED_SEAT).build());
        RecruitChatroomRequest chatroomDto1 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag3.getId()))
                .build();
        RecruitChatroomRequest chatroomDto2 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 2")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag4.getId()))
                .build();
        RecruitChatroomRequest chatroomDto3 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 3")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag4.getId(), tag5.getId()))
                .build();
        RecruitChatroomRequest chatroomDto4 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 4")
                .description("description")
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag4.getId(), tag5.getId(), tag6.getId()))
                .capacity(10)
                .build();
        RecruitChatroomRequest chatroomDto5 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 5")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag4.getId(), tag5.getId(), tag6.getId()))
                .build();
        RecruitChatroomRequest chatroomDto6 = RecruitChatroomRequest.builder()
                .managerId(manager.getId())
                .gameId(game.getId())
                .cheeringClubId(homeClub.getId())
                .title("recruit chatroom 6")
                .description("description")
                .capacity(10)
                .tagIds(List.of(tag1.getId(), tag2.getId(), tag4.getId(), tag5.getId(), tag6.getId()))
                .build();

        chatroomRepository.createRecruitChatroom(chatroomDto1);
        chatroomRepository.createRecruitChatroom(chatroomDto2);
        chatroomRepository.createRecruitChatroom(chatroomDto3);
        chatroomRepository.createRecruitChatroom(chatroomDto4);
        chatroomRepository.createRecruitChatroom(chatroomDto5);
        chatroomRepository.createRecruitChatroom(chatroomDto6);


        RecruitChatroomSearchCondition condition1 = RecruitChatroomSearchCondition.builder()
                .keyword("descrip")
                .build();

        RecruitChatroomSearchCondition condition2 = RecruitChatroomSearchCondition.builder()
                .managerId(manager.getId())
                .cheeringClubId(homeClub.getId())
                .gameId(game.getId())
                .build();
        RecruitChatroomSearchCondition condition3 = RecruitChatroomSearchCondition.builder()
                .managerId(manager.getId())
                .cheeringClubId(homeClub.getId())
                .gameId(game.getId())
                .tagIds(List.of(tag2.getId(), tag4.getId(), tag5.getId()))
                .build();

        Page<RecruitChatroom> found1 = chatroomRepository.findRecruitChatroomsByCondition(condition1, PageRequest.of(0, 10));
        Page<RecruitChatroom> found2 = chatroomRepository.findRecruitChatroomsByCondition(condition2, PageRequest.of(0, 10));
        Page<RecruitChatroom> found3 = chatroomRepository.findRecruitChatroomsByCondition(condition3, PageRequest.of(0, 10));
        log.info("found1 tags: {}", found1.getContent().stream().map(chatroom -> chatroom.getRecruitTags().stream().map(recruitTag -> recruitTag.getTag().getContent()).toList()).toList());
        // then
        assertAll(
                () -> assertEquals(6, found1.getTotalElements()),
                () -> assertEquals(6, found2.getTotalElements()),
                () -> assertEquals(4, found3.getTotalElements())
        );
    }
}
