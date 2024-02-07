package com.ssafy.togeballchatting.service;

import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.entity.ChatMessage;
import com.ssafy.togeballchatting.entity.MessageType;
import com.ssafy.togeballchatting.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        Page<ChatMessage> page = chatMessageRepository.findAllByRoomIdAndTimestampIsGreaterThanEqualOrderByTimestampDesc(roomId, enteredTime, pageable);
        Page<ChatMessageDto> chatMessageDtos = page.map(ChatMessageDto::of);
        List<ChatMessageDto> reversed = new ArrayList<>(chatMessageDtos.getContent());
        Collections.reverse(reversed);
        return new PageImpl<>(reversed, pageable, chatMessageDtos.getTotalElements());
    }

    public Integer countUnreadMessages(Integer roomId, Instant lastReadTimestamp) {
        return chatMessageRepository.countByRoomIdAndTypeNotAndTimestampIsGreaterThan(roomId, MessageType.NOTICE, lastReadTimestamp);
    }

    public ChatMessageDto getLatestChatMessage(Integer roomId) {
        ChatMessage chatMessage = chatMessageRepository.findTopByRoomIdAndTypeNotOrderByTimestampDesc(roomId, MessageType.NOTICE);
        return ChatMessageDto.of(chatMessage);
    }
}
