package com.ssafy.togeballchatting.interceptor;

import com.ssafy.togeballchatting.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService = new JwtService();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        log.info("beforeHandshake");
        String query = request.getURI().getQuery();
        Matcher matcher = authMatcher(query);

        return matcher.find() && jwtService.isTokenValid(matcher.group(1));
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
    public Matcher authMatcher(String query) {
        String pattern = "Authorization([^&]+)";
        Pattern r = Pattern.compile(pattern);
        return r.matcher(query);
    }
}
