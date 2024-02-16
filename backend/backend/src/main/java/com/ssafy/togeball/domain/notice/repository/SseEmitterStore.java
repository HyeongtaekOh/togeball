package com.ssafy.togeball.domain.notice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SseEmitterStore {

    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(Integer userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public SseEmitter getEmitterByUserId(Integer userId) {
        return emitters.get(userId);
    }

    public List<SseEmitter> getAllEmitters() {
        return List.copyOf(emitters.values());
    }

    public void removeEmitter(Integer userId) {
        if (emitters.containsKey(userId)) {
            emitters.remove(userId);
        } else {
            log.error("emitter not found");
        }
    }
}
