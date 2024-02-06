package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.MatchingUser;
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

import java.util.List;

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

    @Scheduled(fixedDelay = 3000)
    public void matching() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

        /**
         * TODO: 매칭 알고리즘 구현
         * 1. 대기 중인 사용자가 2명 이상일 때, 매칭을 시도한다.
         * 2. 매칭 알고리즘을 수행한다.
         * 3. API 서버에 매칭 결과를 전송한다.
         * 4. 매칭된 사용자들에게 매칭 결과를 전송한다.
         */

        // 1. 대기 중인 사용자가 2명 이상일 때, 매칭을 시도한다.
        if (sessions.size() >= 2) {

            // 2. 매칭 알고리즘을 수행한다. (알고리즘 미구현으로 여기서는 대기 중인 사용자들 중 2명을 매칭시킴)
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();

            // 3. API 서버에 모든 매칭 결과를 전송한다. (MatchingRequest 객체를 생성하여 RabbitMQ를 통해 API 서버에 전송)
            //    전송된 매칭 결과는 API 서버에 의해 DB에 저장된다.
            MatchingRequest matchingRequest = MatchingRequest.builder()
                    .title("matching 1")
                    .capacity(userIds.size())
                    .userIds(userIds)
                    .tagIds(List.of(1, 2, 3))  // TODO: 매칭된 유저의 태그 ID 목록을 가져와 설정한다. (중복 제거 필수)
                    .build();
            rabbitService.sendMessage(exchange, routingKey, matchingRequest);
            messagingService.sendMatchingResultToUsers(userIds.subList(0, 2));
        }
    }
}
