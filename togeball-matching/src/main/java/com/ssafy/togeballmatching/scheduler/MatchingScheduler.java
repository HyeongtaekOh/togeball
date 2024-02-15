package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.MatchingResponse;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.service.MatchingService;
import com.ssafy.togeballmatching.service.WaitingTagService;
import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import com.ssafy.togeballmatching.service.rabbit.RabbitMQService;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

    private String adminToken;

    private final RabbitMQService rabbitService;
    private final MessagingService messagingService;
    private final WaitingTagService waitingTagService;
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;
    private final MatchingService matchingService;

    @Scheduled(fixedDelay = 1000 * 30, initialDelay = 1000) //30초마다
    public void matching() {

        log.info("matching scheduler start");

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

        log.info("sessions: {}", sessions);
        log.info("waitingUsers: {}", waitingUsers);

        // 0. 세션 2개 이상일 때만 매칭 수행
        if (sessions.size() >= 2) {

            // 2. 매칭 알고리즘 수행
            List<MatchingRequest> matchings = matchingService.matchUsers(waitingUsers);

            // 3. API 서버에 매칭 결과 전송, 4. 매칭된 사용자들에게 매칭 결과 전송
            for (MatchingRequest matching : matchings) {
//                rabbitService.sendMessage(exchange, routingKey, matching);
                MatchingResponse matchingResponse = getChatroom(matching);
                messagingService.sendMatchingResultToUsers(matching.getUserIds(), matchingResponse);
            }

            waitingQueueService.clearQueue();

        }
    }

    @Scheduled(fixedDelay= 1000 * 5)
    public void gathering() {
        waitingTagService.gathering();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30) //서버 시작 시 작동, 이후 30분마다 갱신
    public String getAdminToken() {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/auth/login";

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request Body 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "admin");
        requestBody.put("password", "admin");

        // Request Entity 생성
        HttpEntity<?> entity = new HttpEntity<>(requestBody.toString(), headers);

        // API 호출
        ResponseEntity responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        // Response Body 출력
        log.info("getAdminToken : {}", responseEntity.getHeaders().get("Authorization"));
        adminToken = responseEntity.getHeaders().get("Authorization").get(0);
        return responseEntity.getHeaders().get("Authorization").get(0);
    }

    public MatchingResponse getChatroom(MatchingRequest matching) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/matching";

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",adminToken);

        // Request Body 설정 (올바르게 직렬화하는 방법 예시)
        HttpEntity<MatchingRequest> entity = new HttpEntity<>(matching, headers);

        // API 호출
        ResponseEntity<MatchingResponse> responseEntity = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                MatchingResponse.class);

        log.info("getChatroomMethod working: {}",responseEntity.getBody());

        // Response Body 출력
        return responseEntity.getBody();
    }

    public MatchingUser getMatchingUser(int userId) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/users/" + userId;

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",adminToken);

        // Request Entity 생성
        HttpEntity entity = new HttpEntity(headers);

        // API 호출
        ResponseEntity<MatchingUser> responseEntity = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        // Response Body 출력
        log.info("getMatchingUser : {}", responseEntity.getBody());
        return responseEntity.getBody();
    }
}
