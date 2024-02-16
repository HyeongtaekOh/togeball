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
    public ChatMessageDto findById(String id) {
        ChatMessage chatMessage = chatMessageRepository.findById(id).orElse(null);
        return chatMessage != null ? ChatMessageDto.of(chatMessage) : null;
    }

    @Transactional(readOnly = true)
    public Page<ChatMessageDto> findAllGameChatMessageByRoomId(Integer roomId, Pageable pageable) {

        Page<ChatMessage> page = chatMessageRepository.findAllByRoomIdOrderByTimestampDesc(roomId, pageable);
        return getReversedPage(pageable, page);
    }


    @Transactional(readOnly = true)
    public Page<ChatMessageDto> findAllByRoomIdAndEnteredTime(Integer roomId, Instant enteredTime, Pageable pageable) {

        Page<ChatMessage> page = chatMessageRepository.findAllByRoomIdAndTimestampIsGreaterThanEqualOrderByTimestampDesc(roomId, enteredTime, pageable);
        return getReversedPage(pageable, page);
    }

    @Transactional(readOnly = true)
    private Page<ChatMessageDto> getReversedPage(Pageable pageable, Page<ChatMessage> page) {
        Page<ChatMessageDto> chatMessageDtos = page.map(ChatMessageDto::of);
        List<ChatMessageDto> reversed = new ArrayList<>(chatMessageDtos.getContent());
        Collections.reverse(reversed);
        return new PageImpl<>(reversed, pageable, chatMessageDtos.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Integer countUnreadMessages(Integer roomId, Instant lastReadTimestamp) {
        return chatMessageRepository.countByRoomIdAndTypeNotAndTimestampIsGreaterThan(roomId, MessageType.NOTICE, lastReadTimestamp);
    }

    @Transactional(readOnly = true)
    public ChatMessageDto getLatestChatMessage(Integer roomId, Instant enteredTimestamp) {
        ChatMessage chatMessage = chatMessageRepository.findTopByRoomIdAndTypeNotAndTimestampIsGreaterThanEqualOrderByTimestampDesc(roomId, MessageType.NOTICE, enteredTimestamp);
        return chatMessage != null ? ChatMessageDto.of(chatMessage) : null;
    }
}
