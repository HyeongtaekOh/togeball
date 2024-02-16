package com.ssafy.togeballchatting.service;

import com.ssafy.togeballchatting.dto.ChatroomStatus;
import com.ssafy.togeballchatting.facade.ChatFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.togeballchatting.dto.ChatMessageDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ChatMessageServiceTest {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatFacade chatFacade;

    @Test
    void getUnreadMessageCountAndLatestChatMessage() {
        // given
        Integer userId = 10;
        Integer roomId = 3673;

        // when
        List<ChatroomStatus> unreadMessageCountAndLatestChatMessage = chatFacade.getUnreadMessageCountAndLatestChatMessage(userId, List.of(roomId));

        // then
        assertNotNull(unreadMessageCountAndLatestChatMessage);
        log.info("unread status: {}", unreadMessageCountAndLatestChatMessage);
    }
}