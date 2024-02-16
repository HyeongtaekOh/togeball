package com.ssafy.togeballmatching.config;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.service.WaitingTagService;
import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.RedisWaitingQueueService;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
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
    private final WaitingQueueService waitingQueueService;

    private final WaitingTagService waitingTagService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        MatchingUser user = (MatchingUser) session.getAttributes().get("user");
        webSocketSessionStoreService.addWebSocketSession(userId, session);
        waitingQueueService.addQueue(user);
        messagingService.sendMatchingStatsToAll();
        waitingTagService.gathering();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("status: {}", status);
        Integer userId = (Integer) session.getAttributes().get("userId");
        webSocketSessionStoreService.removeWebSocketSession(userId);
        waitingQueueService.removeQueue(userId);
        messagingService.sendMatchingStatsToAll();
        waitingTagService.gathering();
    }
}
