package com.ssafy.togeballchatting.controller;

import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.dto.ChatroomUnreadDto;
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

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/users/{userId}/chats/unread")
    public ResponseEntity<?> countUnreadMessages(@PathVariable(value = "userId") Integer userId,
                                                 List<Integer> roomIds) {
        List<ChatroomUnreadDto> response = chatFacade.getUnreadMessageCountAndLatestChatMessage(userId, roomIds);
        return ResponseEntity.ok(response);
    }
}
