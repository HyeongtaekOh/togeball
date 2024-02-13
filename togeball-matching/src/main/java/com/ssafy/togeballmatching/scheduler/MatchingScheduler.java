package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.service.MatchingService;
import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import com.ssafy.togeballmatching.service.rabbit.RabbitMQService;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.matching.routing-key}")
    private String routingKey;

    private final RabbitMQService rabbitService;
    private final MessagingService messagingService;
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;
    private final MatchingService matchingService;

    @Scheduled(fixedDelay = 10000)
    public void matching() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

        if (sessions.size() >= 2) {

            // 1. 유저 목록 받아옴
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();

            // 2. 매칭 알고리즘 수행
            List<MatchingRequest> matchings = matchingService.matchUsers(waitingUsers);

            // 3. API 서버에 매칭 결과 전송, 4. 매칭된 사용자들에게 매칭 결과 전송
            for (MatchingRequest matching : matchings) {
                rabbitService.sendMessage(exchange, routingKey, matching);
                messagingService.sendMatchingResultToUsers(matching.getUserIds());
            }
        }
    }
}