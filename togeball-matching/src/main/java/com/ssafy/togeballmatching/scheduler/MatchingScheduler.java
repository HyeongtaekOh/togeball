package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler {

    private final MessagingService messagingService;
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;

    @Scheduled(fixedDelay = 3000)
    public void matching() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        if (sessions.size() >= 2) {
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();
            messagingService.sendMatchingResultToUsers(userIds.subList(0, 2));
        }
    }
}
