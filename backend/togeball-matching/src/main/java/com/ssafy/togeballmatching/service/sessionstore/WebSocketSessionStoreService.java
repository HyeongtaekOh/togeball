package com.ssafy.togeballmatching.service.sessionstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WebSocketSessionStoreService {

    private final ConcurrentHashMap<Integer, WebSocketSession> store = new ConcurrentHashMap<>();

    public WebSocketSession getWebSocketSession(Integer userId) {
        return store.get(userId);
    }

    public List<WebSocketSession> getAllWebSocketSession() {
        return List.copyOf(store.values());
    }

    public List<WebSocketSession> getWebSocketSessionsByUserIds(List<Integer> userIds) {

        List<WebSocketSession> webSocketSessions = new ArrayList<>();
        for (Integer userId : userIds) {
            webSocketSessions.add(store.get(userId));
        }
        return webSocketSessions;
    }

    public void addWebSocketSession(Integer userId, WebSocketSession webSocketSession) {
        store.put(userId, webSocketSession);
    }

    public void removeWebSocketSession(Integer userId) {
        if (store.containsKey(userId)) {
            store.remove(userId);
        } else {
            log.error("WebSocketSessionStoreService.removeWebSocketSession : userId {} not found", userId);
        }
    }

    public void clear() {
        store.clear();
    }
}
