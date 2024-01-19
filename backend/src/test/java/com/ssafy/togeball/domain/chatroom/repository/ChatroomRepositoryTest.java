package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class ChatroomRepositoryTest {

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Test
    void saveGameChatroomTest() {

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

    @Test
    void findAll() {

        // given
        GameChatroom gameChatroom = GameChatroom.builder()
                .title("game chatroom")
                .gameId(1L)
                .build();
        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .title("recruit chatroom")
                .description("description")
                .capacity(10)
                .build();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title("matching chatroom")
                .matchingId(1L)
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
        chatrooms.stream().allMatch(chatroom -> {
            if (chatroom instanceof GameChatroom) {
                assertEquals(gameChatroom.getTitle(), chatroom.getTitle());
                assertEquals(gameChatroom.getGameId(), ((GameChatroom) chatroom).getGameId());
            } else if (chatroom instanceof RecruitChatroom) {
                assertEquals(recruitChatroom.getTitle(), chatroom.getTitle());
                assertEquals(recruitChatroom.getDescription(), ((RecruitChatroom) chatroom).getDescription());
                assertEquals(recruitChatroom.getCapacity(), ((RecruitChatroom) chatroom).getCapacity());
            } else if (chatroom instanceof MatchingChatroom) {
                assertEquals(matchingChatroom.getTitle(), chatroom.getTitle());
                assertEquals(matchingChatroom.getMatchingId(), ((MatchingChatroom) chatroom).getMatchingId());
            }
            return true;
        });
    }
}