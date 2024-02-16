package com.ssafy.togeball.domain.chatroom.controller;

import com.ssafy.togeball.domain.auth.aop.UserContext;
import com.ssafy.togeball.domain.chatroom.dto.*;
import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatroomController {

    private final ChatroomService chatroomService;

    @GetMapping("/test")
    public ChatroomResponse test() {

        return ChatroomResponse.builder()
                .id(1)
                .title("test")
                .type("test")
                .build();
    }

    @GetMapping("/{chatroomId}")
    public ResponseEntity<?> findChatroomById(@PathVariable(value = "chatroomId") Integer chatroomId) {
        ChatroomResponse chatroom = chatroomService.findChatroomById(chatroomId);
        return ResponseEntity.ok(chatroom);
    }

    @GetMapping("/{chatroomId}/participants")
    public ResponseEntity<?> findParticipantsByChatroomId(@PathVariable(value = "chatroomId") Integer chatroomId) {
        List<UserResponse> participants = chatroomService.findParticipantsByChatroomId(chatroomId);
        return ResponseEntity.ok(participants);
    }

    @GetMapping
    public ResponseEntity<?> findChatroomsByType(@RequestParam String type, Pageable pageable) {

        Page<ChatroomResponse> page = chatroomService.findAllChatroomsByType(type, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/recruit")
    public ResponseEntity<?> findRecruitChatroomsByCondition(RecruitChatroomSearchCondition condition, Pageable pageable) {

        Page<RecruitChatroomResponse> page = chatroomService.findRecruitChatroomsByCondition(condition, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping()
    public ResponseEntity<?> createRecruitChatroom(@RequestBody RecruitChatroomRequest chatroomDto) {

        Integer chatroomId = chatroomService.createRecruitChatroom(chatroomDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{chatroomId}")
                .buildAndExpand(chatroomId)
                .toUri();
        return ResponseEntity.created(location).body(Map.of("id", chatroomId));
    }

    @GetMapping("/game")
    public ResponseEntity<?> findGameChatroomByGameId(@RequestParam(value = "gameId") Integer gameId) {
        GameChatroomResponse chatroom = chatroomService.findGameChatroomByGameId(gameId);
        return ResponseEntity.ok(chatroom);
    }

    @PostMapping("/game")
    public ResponseEntity<?> createGameChatroom(@RequestBody GameChatroomRequest chatroomDto) {

        Integer chatroomId = chatroomService.createGameChatroom(chatroomDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{chatroomId}")
                .buildAndExpand(chatroomId)
                .toUri();
        return ResponseEntity.created(location).body(Map.of("id", chatroomId));
    }

    @PatchMapping("/{chatroomId}")
    public ResponseEntity<?> updateRecruitChatroom(@PathVariable(value = "chatroomId") Integer chatroomId,
                                                   @RequestBody RecruitChatroomRequest chatroomDto) {

        chatroomDto.setId(chatroomId);
        RecruitChatroomResponse chatroom = chatroomService.updateRecruitChatroom(chatroomDto);
        return ResponseEntity.ok(chatroom);
    }

    @DeleteMapping("/{chatroomId}")
    public ResponseEntity<?> deleteChatroom(@PathVariable(value = "chatroomId") Integer chatroomId) {
        chatroomService.deleteChatroom(chatroomId);
        return ResponseEntity.noContent().build();
    }

    @UserContext
    @PostMapping("/{chatroomId}/participants")
    public ResponseEntity<?> joinChatroom(Integer userId,
                                          @PathVariable(value = "chatroomId") Integer chatroomId) {

        chatroomService.joinChatroom(userId, chatroomId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @UserContext
    @DeleteMapping("/{chatroomId}/participants")
    public ResponseEntity<?> leaveChatroom(Integer userId,
                                           @PathVariable(value = "chatroomId") Integer chatroomId) {
        chatroomService.leaveChatroom(userId, chatroomId);
        return ResponseEntity.noContent().build();
    }
}
