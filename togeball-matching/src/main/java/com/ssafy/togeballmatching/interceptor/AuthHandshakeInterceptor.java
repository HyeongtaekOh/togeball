package com.ssafy.togeballmatching.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request.getURI().getQuery() == null || request.getURI().getQuery().split("userId=").length < 2) {
            return false;
        }

        String userIdParameter = request.getURI().getQuery().split("userId=")[1];
        try {
            int userId = Integer.parseInt(userIdParameter);
            log.info("userId: {}", userId);
            attributes.put("userId", userId);
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
