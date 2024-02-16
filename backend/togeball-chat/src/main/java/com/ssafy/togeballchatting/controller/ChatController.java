package com.ssafy.togeballchatting.controller;

import com.ssafy.togeballchatting.aop.UserContext;
import com.ssafy.togeballchatting.dto.ChatMessageDto;
import com.ssafy.togeballchatting.dto.ChatReadDto;
import com.ssafy.togeballchatting.dto.ChatroomStatus;
import com.ssafy.togeballchatting.exception.NotParticipatingException;
import com.ssafy.togeballchatting.facade.ChatFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @UserContext
    @GetMapping("/chats/{roomId}")
    public ResponseEntity<?> findChatMessagePageByRoomId(Integer userId,
                                                         HttpServletRequest request,
                                                         @PathVariable(value = "roomId") Integer roomId,
                                                         Pageable pageable) {
        Page<ChatMessageDto> response = chatFacade.findChatMessagePageByRoomId(roomId, userId, pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

    @UserContext
    @GetMapping("/chats/{roomId}/game")
    public ResponseEntity<?> findGameChatMessagePageByRoomId(Integer userId,
                                                             HttpServletRequest request,
                                                             @PathVariable(value = "roomId") Integer roomId,
                                                             Pageable pageable) {
        Page<ChatMessageDto> response = chatFacade.findGameChatMessagePageByRoomId(roomId, pageable);
        log.info("response: {}", response.getContent());
        return ResponseEntity.ok(response);
    }

    @UserContext
    @GetMapping("/me/chats/unread")
    public ResponseEntity<?> countUnreadMessages(Integer userId,
                                                 HttpServletRequest request,
                                                 @RequestParam(value = "roomId", required = false) List<Integer> roomIds) {

        log.info("userId: {}, roomIds: {}", userId, roomIds);
        List<ChatroomStatus> response = chatFacade.getUnreadMessageCountAndLatestChatMessage(userId, roomIds);
        return ResponseEntity.ok(response);
    }

    @UserContext
    @PostMapping("/me/chats/{roomId}/read")
    public ResponseEntity<?> readChatMessages(Integer userId,
                                              HttpServletRequest request,
                                              @PathVariable(value = "roomId") Integer roomId,
                                              @RequestBody ChatReadDto chatReadDto) {
        chatFacade.updateReadStatus(userId, roomId, chatReadDto);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotParticipatingException.class)
    public ResponseEntity<?> handleNotParticipatingException(NotParticipatingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
