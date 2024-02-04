package com.ssafy.togeballchatting.facade;

import com.ssafy.togeballchatting.dto.ChatHistoryDto;
import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.service.ChatHistoryService;
import com.ssafy.togeballchatting.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatHistoryService chatHistoryService;
    private final ChatMessageService chatMessageService;

    public Page<ChatMessageDto> findChatMessagePageByRoomId(Integer roomId, Integer userId, Pageable pageable) {
        log.info("roomId: {}, userId: {}, pageable: {}", roomId, userId, pageable);
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            Instant now = Instant.now();
            chatHistoryDto = chatHistoryService.save(ChatHistoryDto.builder()
                    .roomId(roomId)
                    .userId(userId)
                    .enteredTimestamp(now)
                    .lastReadTimestamp(now)
                    .build());
        }
        log.info("chatHistoryDto: {}", chatHistoryDto);
        Page<ChatMessageDto> page = chatMessageService.findAllByRoomIdAndEnteredTime(roomId, chatHistoryDto.getEnteredTimestamp(), pageable);
        Instant lastReadTimestamp = page.getContent().get(page.getContent().size() - 1).getTimestamp();

        return page;
    }

    public Integer countUnreadMessages(Integer roomId, Integer userId) {
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            return 0;
        }
        return chatMessageService.countUnreadMessages(roomId, chatHistoryDto.getLastReadTimestamp());
    }
}
