package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.*;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

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
    private ChatroomRepository chatroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveGameChatroom() {

        // given
        GameChatroom gameChatroom1 = GameChatroom.builder()
                .title("game chatroom 1")
                .gameId(1L)
                .build();
        GameChatroom gameChatroom2 = GameChatroom.builder()
                .title("game chatroom 2")
                .gameId(2L)
                .build();

        // when
        GameChatroom saved1 = chatroomRepository.save(gameChatroom1);
        GameChatroom saved2 = chatroomRepository.save(gameChatroom2);


        // then
        log.info("saved1: id = {}, title = {}, gameId = {}", gameChatroom1.getId(), gameChatroom1.getTitle(), gameChatroom1.getGameId());
        assertEquals(gameChatroom1.getTitle(), saved1.getTitle());
        assertEquals(gameChatroom1.getGameId(), saved1.getGameId());
        assertInstanceOf(GameChatroom.class, saved1);
        log.info("saved2: id = {}, title = {}, gameId = {}", gameChatroom2.getId(), gameChatroom2.getTitle(), gameChatroom2.getGameId());
        assertEquals(gameChatroom2.getTitle(), saved2.getTitle());
        assertEquals(gameChatroom2.getGameId(), saved2.getGameId());
        assertInstanceOf(GameChatroom.class, saved2);
    }

    @Test
    void saveRecruitChatroom() {

        // given
        User manager = User.builder()
                .email("test@gmail.com")
                .password("password")
                .nickname("nickname")
                .build();

        RecruitChatroom recruitChatroom1 = RecruitChatroom.builder()
                .title("recruit chatroom 1")
                .description("description")
                .capacity(10)
                .build();
        RecruitChatroom recruitChatroom2 = RecruitChatroom.builder()
                .title("recruit chatroom 2")
                .description("description")
                .capacity(10)
                .build();
        recruitChatroom1.setManager(manager);
        recruitChatroom2.setManager(manager);

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
                .gameId(1L)
                .build();

        // when
        Chatroom saved1 = chatroomRepository.save(gameChatroom1);
        Chatroom found1 = chatroomRepository.findById(saved1.getId()).orElse(null);

        // then
        assertInstanceOf(GameChatroom.class, found1);
        assertEquals(gameChatroom1.getTitle(), found1.getTitle());
        assertEquals(gameChatroom1.getGameId(), ((GameChatroom) found1).getGameId());
    }

    // GameChatroom, RecruitChatroom, MatchingChatroom 3개의 채팅방을 저장하고 findAll()로 모두 조회
    @Test
    void findAll() {

        // given
        GameChatroom gameChatroom = GameChatroom.builder()
                .title("game chatroom")
                .gameId(1L)
                .build();
        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .manager(
                        User.builder()
                                .email("test@test.com")
                                .password("password")
                                .nickname("nickname")
                                .build()
                )
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title("matching chatroom")
                .matching(
                        Matching.builder()
                                .title("matching")
                                .capacity(10)
                                .build()
                )
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
                assertEquals(gameChatroom.getGameId(), ((GameChatroom) chatroom).getGameId());
            } else if (chatroom instanceof RecruitChatroom) {
                assertEquals(recruitChatroom.getTitle(), chatroom.getTitle());
                assertEquals(recruitChatroom.getDescription(), ((RecruitChatroom) chatroom).getDescription());
                assertEquals(recruitChatroom.getCapacity(), ((RecruitChatroom) chatroom).getCapacity());
            } else if (chatroom instanceof MatchingChatroom) {
                assertEquals(matchingChatroom.getTitle(), chatroom.getTitle());
            }
        });
    }
}