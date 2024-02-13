package com.ssafy.togeball.domain.notice.service;

import com.ssafy.togeball.domain.notice.repository.SseEmitterStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final long SSE_SESSION_TIMEOUT = 60 * 60 * 1000;

    private final SseEmitterStore sseEmitterStore;

    public SseEmitter subscribe(Integer userId) {
        SseEmitter emitter = new SseEmitter(SSE_SESSION_TIMEOUT);
        sseEmitterStore.addEmitter(userId, emitter);
        emitter.onCompletion(() -> sseEmitterStore.removeEmitter(userId));
        emitter.onTimeout(() -> sseEmitterStore.removeEmitter(userId));
        try {
            emitter.send(SseEmitter.event().name("connected").data("connected"));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendToUser(Integer userId, String eventName , Object data) {
        SseEmitter emitter = sseEmitterStore.getEmitterByUserId(userId);
        log.info("send to user: {}", userId);
        log.info("emitter: {}", emitter);
        if (emitter != null) {
            try {
                log.info("match ressult: {}", data);
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        }
    }

    public void sendToAll(String eventName , Object data) {
        sseEmitterStore.getAllEmitters().forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        });
    }

    public void sendToUsers(List<Integer> userIds, String eventName , Object data) {

        userIds.forEach(userId -> {
            SseEmitter emitter = sseEmitterStore.getEmitterByUserId(userId);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event()
                            .name(eventName)
                            .data(data));
                } catch (Exception e) {
                    log.error("error: {}", e.getMessage());
                }
            }
        });
    }
}
