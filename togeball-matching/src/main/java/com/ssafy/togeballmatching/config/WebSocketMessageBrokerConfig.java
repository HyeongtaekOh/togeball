package com.ssafy.togeballmatching.config;

import com.ssafy.togeballmatching.interceptor.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 매칭 서비스에 사용할 웹 소켓 엔드포인트를 "/match"로 설정합니다.
        registry
                .addEndpoint("/matching")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setInterceptors(new AuthHandshakeInterceptor());
    }
}
