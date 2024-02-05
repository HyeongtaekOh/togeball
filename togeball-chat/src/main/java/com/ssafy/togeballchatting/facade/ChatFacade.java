package com.ssafy.togeballchatting.facade;

import com.ssafy.togeballchatting.dto.ChatHistoryDto;
import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.dto.ChatroomUnreadDto;
import com.ssafy.togeballchatting.service.ChatHistoryService;
import com.ssafy.togeballchatting.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatHistoryService chatHistoryService;
    private final ChatMessageService chatMessageService;

    public Page<ChatMessageDto> findChatMessagePageByRoomId(Integer roomId, Integer userId, Pageable pageable) {
        log.info("roomId: {}, userId: {}, pageable: {}", roomId, userId, pageable);

        // 사용자가 채팅방에 처음 들어온 경우, 채팅방 입장 시간을 기록
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
        log.info("page.getContent(): {}", page.getContent());
        if (page.getContent().isEmpty()) {
            return page;
        }

        // 사용자가 채팅방에서 읽은 메시지 중 가장 최근 메시지의 타임스탬프를 업데이트
        Instant lastReadTimestamp = page.getContent().get(page.getContent().size() - 1).getTimestamp();
        if (lastReadTimestamp.isAfter(chatHistoryDto.getLastReadTimestamp())) {
            chatHistoryDto.setLastReadTimestamp(lastReadTimestamp);
            chatHistoryService.save(chatHistoryDto);
        }

        return page;
    }

    public List<ChatroomUnreadDto> getUnreadMessageCountAndLatestChatMessage(Integer userId, List<Integer> roomIds) {
        List<ChatroomUnreadDto> response = new ArrayList<>();
        for (Integer roomId : roomIds) {
            Integer unreadCount = countUnreadMessages(roomId, userId);
            ChatMessageDto latestChatMessage = getLatestChatMessage(roomId);
            response.add(ChatroomUnreadDto.builder()
                    .roomId(roomId)
                    .unreadCount(unreadCount)
                    .latestChatMessage(latestChatMessage)
                    .build());
        }
        log.info("response: {}", response);
        return response;
    }

    public Integer countUnreadMessages(Integer roomId, Integer userId) {
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            return 0;
        }
        return chatMessageService.countUnreadMessages(roomId, chatHistoryDto.getLastReadTimestamp());
    }

    public ChatMessageDto getLatestChatMessage(Integer roomId) {
        return chatMessageService.getLatestChatMessage(roomId);
    }
}
