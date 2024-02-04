package com.ssafy.togeball.domain.chatroom.controller;

import com.ssafy.togeball.domain.chatroom.dto.*;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
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

        Chatroom chatroom = chatroomService.getTestChatroom();

        return ChatroomResponse.builder()
                .id(chatroom.getId())
                .title(chatroom.getTitle())
                .type(chatroom.getType())
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

    @PostMapping("/{chatroomId}/participants")
    public ResponseEntity<?> joinChatroom(@PathVariable(value = "chatroomId") Integer chatroomId,
                                          @RequestBody Integer userId) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
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

    @DeleteMapping("/{chatroomId}/participants/{userId}")
    public ResponseEntity<?> leaveChatroom(@PathVariable(value = "chatroomId") Integer chatroomId,
                                           @PathVariable(value = "userId") Integer userId) {
        chatroomService.leaveChatroom(userId, chatroomId);
        return ResponseEntity.noContent().build();
    }
}
