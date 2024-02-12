package com.ssafy.togeballmatching.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.togeballmatching.config.WebConfig;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.dto.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.swing.*;
import java.io.DataInput;
import java.util.*;

@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 1. 클라이언트(프론트)에서 유저가 매칭 받기 버튼을 클릭해서 여기로 왔다고 치자

        // 2. 로그인한 유저의 jwtToken 가져옴

        // 2-1. Header에서 Authorization의 bearer 토큰을 가져옴
//        String bearerToken = request.getHeaders().get("Authorization").toString();
//        String jwtToken;
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            jwtToken = bearerToken.substring(7);
//            log.info("jwtToken : {}", jwtToken);
//        } else {
//            log.info("토큰이 안 들어왔어요!!");
//            return false;
//        }

        // 2-2. 테스트를 위해 쿼리 스트링으로 토큰을 받아옴
        if (request.getURI().getQuery() == null) return false;
        String jwtToken = request.getURI().getQuery().split("token=")[1];

        // JWT 토큰 처리 로직 시작
        String[] parts = jwtToken.split("\\."); // JWT 토큰 파싱
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]); // 디코딩
        String decodedPayload = new String(decodedBytes);

        JSONParser parser = new JSONParser();
        JSONObject payload = (JSONObject) parser.parse(decodedPayload);
        String id = payload.get("id").toString(); //유저 id
        Integer userId = Integer.parseInt(id);

        attributes.put("userId", userId);

//        Spring WebClient는 웹으로 API를 호출하기 위해 사용되는 Http Client 모듈 중 하나
//        Spring에서 RestTemplate보다 권장하는 방식
//        Single Thread, Non-Blocking 방식으로 응답 속도가 빠름
//        JSON을 쉽게 받을 수 있음

        // 3. token에서 userId를 추출하여 WebClient로 백으로 유저 정보를 요청 (테스트: https://localhost:8080/webClientTest.html)
        WebClient webClient = WebConfig.getBaseUrl();
        MatchingUser user = webClient.get()
                .uri("/api/users/" + userId)
                .retrieve()
                .bodyToMono(MatchingUser.class)
                .block();
        log.info("{},{},{},{}",user.getUserId(),user.getNickname(),user.getProfileImage(),user.getTags());
        log.info("{}",user.getTags().get(0).getType()); //PREFERRED_TEAM
        boolean temp = user.getTags().get(0) instanceof Tag; //true

        attributes.put("tags", user.getTags());

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
