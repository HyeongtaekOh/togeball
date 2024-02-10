package com.ssafy.togeballchatting.controller;

import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.dto.ChatroomStatus;
import com.ssafy.togeballchatting.exception.NotParticipatingException;
import com.ssafy.togeballchatting.facade.ChatFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-server")
public class ChatController {

    private final ChatFacade chatFacade;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/chats/{roomId}")
    public ResponseEntity<?> findChatMessagePageByRoomId(@PathVariable(value = "roomId") Integer roomId,
                                                      Integer userId,
                                                      Pageable pageable) {
        Page<ChatMessageDto> response = chatFacade.findChatMessagePageByRoomId(roomId, userId, pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/chats/{roomId}/game")
    public ResponseEntity<?> findGameChatMessagePageByRoomId(@PathVariable(value = "roomId") Integer roomId,
                                                      Pageable pageable) {
        Page<ChatMessageDto> response = chatFacade.findGameChatMessagePageByRoomId(roomId, pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/chats/unread")
    public ResponseEntity<?> countUnreadMessages(@PathVariable(value = "userId") Integer userId,
                                                 @RequestParam(value = "roomId", required = false) List<Integer> roomIds) {
        log.info("userId: {}, roomIds: {}", userId, roomIds);
        List<ChatroomStatus> response = chatFacade.getUnreadMessageCountAndLatestChatMessage(userId, roomIds);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(NotParticipatingException.class)
    public ResponseEntity<?> handleNotParticipatingException(NotParticipatingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
