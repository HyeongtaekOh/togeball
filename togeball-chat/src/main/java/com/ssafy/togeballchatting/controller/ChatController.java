package com.ssafy.togeballchatting.controller;

import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.facade.ChatFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-server")
public class ChatController {

    private final ChatFacade chatFacade;

    @GetMapping("/chats/{roomId}")
    public ResponseEntity<?> findChatMessagePageByRoomId(@PathVariable(value = "roomId") Integer roomId,
                                                      Integer userId,
                                                      Pageable pageable) {
        Page<ChatMessageDto> response = chatFacade.findChatMessagePageByRoomId(roomId, userId, pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

}
