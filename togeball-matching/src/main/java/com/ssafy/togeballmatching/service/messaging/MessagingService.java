package com.ssafy.togeballmatching.service.messaging;

import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final WebSocketSessionStoreService webSocketSessionStoreService;

    public void sendMessage(Integer userId, String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        webSocketSessionStoreService.getWebSocketSession(userId).sendMessage(textMessage);
    }

    public void sendMatchingStatsToAll(){

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage("waitings:" + userIds));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMatchingResultToUsers(List<Integer> userIds){

        List<WebSocketSession> sessions = webSocketSessionStoreService.getWebSocketSessionsByUserIds(userIds)
                .stream()
                .filter(session -> userIds.contains((Integer) session.getAttributes().get("userId")))
                .toList();

        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage("matched:" + userIds));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
