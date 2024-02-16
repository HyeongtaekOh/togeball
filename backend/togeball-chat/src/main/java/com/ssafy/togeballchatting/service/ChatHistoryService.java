package com.ssafy.togeballchatting.service;

import com.ssafy.togeballchatting.dto.ChatHistoryDto;
import com.ssafy.togeballchatting.dto.ChatroomJoinMessage;
import com.ssafy.togeballchatting.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    @Transactional
    public ChatHistoryDto save(ChatHistoryDto chatHistoryDto) {
        return ChatHistoryDto.of(chatHistoryRepository.save(chatHistoryDto.toEntity()));
    }

    @Transactional(readOnly = true)
    public ChatHistoryDto findChatHistoryByRoomIdAndUserId(Integer roomId, Integer userId) {
        return chatHistoryRepository.findByRoomIdAndUserId(roomId, userId)
                .map(ChatHistoryDto::of).orElse(null);
    }

    @Transactional
    public void deleteByRoomIdAndUserId(Integer roomId, Integer userId) {
        chatHistoryRepository.deleteByRoomIdAndUserId(roomId, userId);
    }
}
