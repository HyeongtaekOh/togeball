package com.ssafy.togeball.domain.chatroom.controller;

import com.ssafy.togeball.domain.auth.aop.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatSseController {

    @UserContext
    @GetMapping(value = "/api/chatrooms/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Integer userId) {
        log.info("userId: {}", userId);
        SseEmitter emitter = new SseEmitter();
        try {
            emitter.send("connected");
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
        }
        return ResponseEntity.ok(emitter);
    }
}
