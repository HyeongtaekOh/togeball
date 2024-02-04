package com.ssafy.togeballchatting.service;

import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.entity.ChatMessage;
import com.ssafy.togeballchatting.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageDto save(ChatMessageDto chatMessage) {

        ChatMessage message = chatMessageRepository.save(chatMessage.toEntity());
        return ChatMessageDto.of(message);
    }

    @Transactional(readOnly = true)
    public Page<ChatMessageDto> findAllByRoomIdAndEnteredTime(Integer roomId, Instant enteredTime, Pageable pageable) {

        Page<ChatMessage> page = chatMessageRepository.findAllByRoomIdAndTimestampIsGreaterThanEqual(roomId, enteredTime, pageable);
        page.forEach(chatMessage -> log.info("chatMessage: {}", chatMessage));
        return page
                .map(chatMessage -> ChatMessageDto.builder()
                        .roomId(chatMessage.getRoomId())
                        .senderId(chatMessage.getSenderId())
                        .type(chatMessage.getType())
                        .content(chatMessage.getContent())
                        .timestamp(chatMessage.getTimestamp())
                        .build());
    }

    public Integer countUnreadMessages(Integer roomId, Instant lastReadTimestamp) {
        return chatMessageRepository.countByRoomIdAndTimestampIsGreaterThan(roomId, lastReadTimestamp);
    }
}
