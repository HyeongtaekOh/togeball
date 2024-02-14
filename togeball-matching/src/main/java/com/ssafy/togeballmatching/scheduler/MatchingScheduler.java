package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.config.WebConfig;
import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.service.MatchingService;
import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.RedisWaitingQueueService;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

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
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;
    private final MatchingService matchingService;

    @Scheduled(fixedDelay = 15000)
    public void matching() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

//        if (sessions.size() >= 2) {

            // 1. 유저 목록 받아옴
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();
            // 2. 매칭 알고리즘 수행
            List<MatchingRequest> matchings = matchingService.matchUsers(waitingUsers);

            // 3. API 서버에 매칭 결과 전송, 4. 매칭된 사용자들에게 매칭 결과 전송
            for (MatchingRequest matching : matchings) {
                rabbitService.sendMessage(exchange, routingKey, matching);

            String chatroomId = getChatroomId(matching);

                List<MatchingUser> participants = new ArrayList<>();
                for (int i : matching.getUserIds()) {
                    participants.add(getMatchingUser(i));
                }

                messagingService.sendMatchingResultToUsers(matching.getTitle(), matching.getUserIds(), chatroomId, participants);
            }
//            waitingQueueService.clearQueue();
//        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30, initialDelay = 0) //서버 시작 시 작동, 이후 30분마다 갱신
    public String getAdminToken() {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/auth/login";

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request Body 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("email","ay@com.com");
        requestBody.put("password","1234");

        // Request Entity 생성
        HttpEntity entity = new HttpEntity(requestBody.toString(), headers);

        // API 호출
        ResponseEntity responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        // Response Body 출력
        System.out.println(responseEntity.getHeaders().get("Authorization"));
        return responseEntity.getHeaders().get("Authorization").get(0);
    }

    public String getChatroomId(MatchingRequest matching) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/matching";

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",adminToken);

        // Request Body 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("matchingRequest",matching);

        // Request Entity 생성
        HttpEntity entity = new HttpEntity(requestBody.toString(), headers);

        // API 호출
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        // Response Body 출력
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public MatchingUser getMatchingUser(int userId) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/user/" + userId;

        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",adminToken);

        // Request Entity 생성
        HttpEntity entity = new HttpEntity(headers);

        // API 호출
        ResponseEntity<MatchingUser> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});

        // Response Body 출력
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Value("admin")
    private String email;
    @Value("admin")
    private String password;
}