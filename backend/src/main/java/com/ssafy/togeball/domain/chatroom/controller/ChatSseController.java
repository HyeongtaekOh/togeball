package com.ssafy.togeball.domain.chatroom.controller;

import com.ssafy.togeball.domain.auth.aop.UserContext;
import com.ssafy.togeball.domain.chatroom.dto.ChatMessage;
import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatSseController {

    private final long SSE_SESSION_TIMEOUT = 60 * 60 * 1000;
    private final Map<Integer, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final ChatroomService chatroomService;

    @UserContext
    @GetMapping(value = "/api/chatrooms/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Integer userId) {
        log.info("userId: {}", userId);
        SseEmitter emitter = new SseEmitter(SSE_SESSION_TIMEOUT);
        try {
            emitter.send(SseEmitter.event().name("connected").data("connected"));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
        return ResponseEntity.ok(emitter);
    }

    @RabbitListener(queues = "${rabbitmq.notification.queue}")
    private void sendNotification(ChatMessage message) {
        log.info("message: {}", message);
        List<Integer> participantIds = chatroomService.findParticipantsByChatroomId(message.getRoomId())
                .stream()
                .map(UserResponse::getId)
                .toList();
        participantIds.forEach(userId -> {
            SseEmitter emitter = sseEmitters.get(userId);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("message").data(message));
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    sseEmitters.remove(userId);
                }
            }
        });
    }
}
