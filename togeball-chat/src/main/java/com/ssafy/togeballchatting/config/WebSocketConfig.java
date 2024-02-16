package com.ssafy.togeballchatting.config;

import com.ssafy.togeballchatting.interceptor.AuthHandshakeInterceptor;
import com.ssafy.togeballchatting.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${websocket.relay.host}")
    private String relayHost;

    @Value("${websocket.relay.port}")
    private int relayPort;

    @Value("${websocket.relay.client.login}")
    private String clientLogin;

    @Value("${websocket.relay.client.passcode}")
    private String clientPasscode;

    @Value("${websocket.relay.system.login}")
    private String systemLogin;

    @Value("${websocket.relay.system.passcode}")
    private String systemPasscode;

    private final JwtService jwtService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 채팅 서비스에 사용할 웹 소켓 엔드포인트를 "/chat"으로 설정합니다.
        registry
                .addEndpoint("/chat-server/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setInterceptors(new AuthHandshakeInterceptor(jwtService));
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setPathMatcher(new AntPathMatcher("."))
                .setApplicationDestinationPrefixes("/pub")
                .enableStompBrokerRelay("/topic")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPasscode)
                .setSystemLogin(systemLogin)
                .setSystemPasscode(systemPasscode);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("SessionId: {}", headerAccessor.getSessionId());
        log.info("headerAccessor: {}", headerAccessor);
        headerAccessor.getSessionAttributes().put("userId", headerAccessor.getFirstNativeHeader("Authorization"));
        log.info("Authorization header: {}", headerAccessor.getFirstNativeHeader("Authorization"));
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("SessionId: {}", headerAccessor.getSessionId());
        log.info("headerAccessor: {}", headerAccessor);
        log.info("Authorization header: {}", headerAccessor.getFirstNativeHeader("Authorization"));
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            log.info("userId: {}", sessionAttributes.get("userId"));
        }
        log.info("Received a new web socket disconnection");
    }
}
