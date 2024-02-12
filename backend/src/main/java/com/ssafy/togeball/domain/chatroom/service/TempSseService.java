package com.ssafy.togeball.domain.chatroom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TempSseService {

    private final Map<Integer, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public void addSseEmitter(Integer userId, SseEmitter emitter) {
        sseEmitters.put(userId, emitter);
    }

    public void removeSseEmitter(Integer userId) {
        sseEmitters.remove(userId);
    }

    public SseEmitter getSseEmitter(Integer userId) {
        return sseEmitters.get(userId);
    }
}
