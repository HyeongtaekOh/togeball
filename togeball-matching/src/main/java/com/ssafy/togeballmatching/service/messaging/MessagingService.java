package com.ssafy.togeballmatching.service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingService {

    private final WebSocketSessionStoreService webSocketSessionStoreService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Integer userId, String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        webSocketSessionStoreService.getWebSocketSession(userId).sendMessage(textMessage);
    }
    public void sendTagsToAll(List<String> sortedTagIds, Map<String, Integer> sortedTags) {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        sessions.forEach(session -> {
            try {
                Map<String, Object> combinedData = new HashMap<>();
                combinedData.put("hashtags", sortedTagIds);
                combinedData.put("counts", sortedTags);
                String json = objectMapper.writeValueAsString(combinedData);
                TextMessage textMessage = new TextMessage(json);
                session.sendMessage(textMessage);
//                String counts = objectMapper.writeValueAsString(sortedTags);
//                session.sendMessage(new TextMessage(counts));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMatchingStatsToAll(){

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();
        sessions.forEach(session -> {
//            try {
//                session.sendMessage(new TextMessage("waitings:" + userIds));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
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
