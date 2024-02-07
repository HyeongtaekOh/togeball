package com.ssafy.togeballmatching.interceptor;

import com.ssafy.togeballmatching.config.WebConfig;
import com.ssafy.togeballmatching.dto.MatchingUser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import reactor.core.publisher.Mono;

import java.security.SignatureException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 1. 클라이언트(프론트)에서 유저가 매칭 받기 버튼을 클릭해서 여기로 왔다고 치자

        // 2. 로그인한 유저라면 Headers의 Authorization에 bearerToken이 있을 것

        // 2-1. bearerToken을 받아오는 방법 (1)
//        String bearerToken = request.getHeaders().get("Authorization").toString();
//        String token;
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            token = bearerToken.substring(7);
//        }

        // 2-2. bearerToken을 받아오는 방법 (2) - 이렇게 하면 바로 복호화까지 되는 듯?
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user;
        if (authentication != null && authentication.getName() != null) user = authentication.getName();
        else throw new RuntimeException("Authentication 정보가 없습니다.");

        attributes.put("userId", user); //좀 더 생각해보기

        /*
            Spring WebClient는 웹으로 API를 호출하기 위해 사용되는 Http Client 모듈 중 하나
            Spring에서 RestTemplate보다 권장하는 방식
            Single Thread, Non-Blocking 방식으로 응답 속도가 빠름
            JSON을 쉽게 받을 수 있음
         */

        // 3. token에서 userId를 추출하여 WebClient로 백으로 유저 정보를 요청
        // 정보 요청은 https://localhost:8080/webClientTest.html
        // (근데 그냥 accessToken을 바로 보내서 백에서 처리하게 하면 안 되나?)

            WebClient webClient = WebConfig.getBaseUrl();
            ResponseEntity<JSONObject> matchingUser = webClient.get()
                    .uri("/api/users/" + user)
                    .retrieve()
                    .toEntity(JSONObject.class) //String.class로도 가능
                    .block();
            String email = matchingUser.getBody().get("email").toString();
            log.info("statusCode : {}", matchingUser.getStatusCode());
            log.info("header : {}", matchingUser.getHeaders()); //jwt token은 헤더에 담겨 오나...? 뭐라고 담겨 오지
            log.info("body : {}", matchingUser.getBody());
            log.info("email : {}", email);
            //WebClient를 이용해 유저 정보 요청 끝

        try {

        } catch (NumberFormatException e) {
            log.error("NumberFormatException: {}", e);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
