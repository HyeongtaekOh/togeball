package com.ssafy.togeballmatching.interceptor;

import com.ssafy.togeballmatching.config.WebConfig;
import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.service.queue.RedisWaitingQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request.getURI().getQuery() == null || request.getURI().getQuery().split("token=").length < 2) {
            return false;
        }

        String query = request.getURI().getQuery();
        Matcher matcher = tokenMatcher(query);
//        log.info("query: {}", query);
        if (!matcher.find()) {
            log.error("beforeHandshake: Authorization is not found");
            return false;
        }
        String accessToken = matcher.group(1);

        String[] parts = accessToken.split("\\."); // JWT 토큰 파싱
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]); // 디코딩
        String decodedPayload = new String(decodedBytes);
        JSONParser parser = new JSONParser();
        JSONObject payload = (JSONObject) parser.parse(decodedPayload);
        String id = payload.get("id").toString(); //유저 id
        Integer userId = Integer.parseInt(id);
        attributes.put("userId", userId);

        WebClient webClient = WebConfig.getBaseUrl(); // token에서 추출한 userId로 WebClient를 이용해 유저 정보를 요청
        MatchingUser user = webClient.get()
                .uri("/api/users/" + userId)
//                .uri("/api/users/me")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(MatchingUser.class)
                .block();
//        log.info("{},{},{},{}",user.getUserId(),user.getNickname(),user.getProfileImage(),user.getTags());
//        log.info("{}",user.getTags().get(0).getType()); //PREFERRED_TEAM
//        boolean temp = user.getTags().get(0) instanceof Tag; //true

        attributes.put("userId", user.getUserId());
        attributes.put("tags", user.getTags());
        attributes.put("user", user);

        return true;
    }

    public Matcher tokenMatcher(String query) {
        String pattern = "token=([^&]+)";
        Pattern r = Pattern.compile(pattern);
        return r.matcher(query);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
