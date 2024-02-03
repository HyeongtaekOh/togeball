package com.ssafy.togeballmatching.config;

import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final MessagingService messagingService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        webSocketSessionStoreService.addWebSocketSession(userId, session);
        messagingService.sendMatchingStatsToAll();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("status: {}", status);
        Integer userId = (Integer) session.getAttributes().get("userId");
        webSocketSessionStoreService.removeWebSocketSession(userId);
        messagingService.sendMatchingStatsToAll();
    }
}
