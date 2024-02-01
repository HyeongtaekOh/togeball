package com.ssafy.togeballchatting.controller;

import com.ssafy.togeballchatting.dto.ChatHistoryDto;
import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.service.ChatHistoryService;
import com.ssafy.togeballchatting.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-server")
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatHistoryService chatHistoryService;

    @GetMapping("/chats/{roomId}")
    public ResponseEntity<?> findChatMessagePageByRoomId(@PathVariable(value = "roomId") Integer roomId,
                                                      Integer userId,
                                                      Pageable pageable) {
        log.info("roomId: {}, userId: {}, pageable: {}", roomId, userId, pageable);
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            chatHistoryDto = chatHistoryService.save(ChatHistoryDto.builder()
                    .roomId(roomId)
                    .userId(userId)
                    .firstMessageTimestamp(Instant.now())
                    .build());
        }
        log.info("chatHistoryDto: {}", chatHistoryDto);
        Page<ChatMessageDto> response = chatMessageService.findAllByRoomIdAndFirstMessageTime(roomId, chatHistoryDto.getFirstMessageTimestamp(), pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

}
