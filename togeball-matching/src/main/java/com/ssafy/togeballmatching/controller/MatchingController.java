package com.ssafy.togeballmatching.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchingController {

    @MessageMapping("/matching")
    public void connect(WebSocketSession session) throws IOException {
        log.info("session: {}", session);
        session.sendMessage(new TextMessage("success"));
    }
}
