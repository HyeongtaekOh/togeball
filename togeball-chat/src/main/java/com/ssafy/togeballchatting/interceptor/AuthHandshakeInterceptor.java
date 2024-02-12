package com.ssafy.togeballchatting.interceptor;

import com.ssafy.togeballchatting.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService = new JwtService();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        log.info("beforeHandshake");
        String query = request.getURI().getQuery();
        String token = query.split("=")[1];

        log.info("token: {}", token);
        return jwtService.isTokenValid(token);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
