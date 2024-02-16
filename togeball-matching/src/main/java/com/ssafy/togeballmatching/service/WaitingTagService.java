package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.service.messaging.MessagingService;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import com.ssafy.togeballmatching.service.rabbit.RabbitMQService;
import com.ssafy.togeballmatching.service.sessionstore.WebSocketSessionStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingTagService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.matching.routing-key}")
    private String routingKey;

    private final RabbitMQService rabbitService;
    private final MessagingService messagingService;
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;

    public void gathering() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

        // 대기 중인 사용자가 1명 이상일 때, 매칭 시도 중인 유저의 해시태그들
        if (sessions.size() >= 1) {
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();
            List<List<Tag>> tags = new ArrayList<>(); // 유저별 태그 받을 리스트
            Map<String, Integer> userTags = new HashMap<>(); //태그명, 빈도수

            for (int i=0; i<userIds.size(); i++) { //태그를 받아옴
                List<Tag> tag = (ArrayList) sessions.get(i).getAttributes().get("tags");
                for (int j=0; j<tag.size(); j++) {
//                    int id = tag.get(i).getId();
                    String id = tag.get(j).getContent();
                    if (userTags.containsKey(id)) userTags.put(id,userTags.get(id)+1);
                    else userTags.put(id, 1);
                }
                tags.add(tag);
            }

//            log.info("userId(0):{}",userIds.get(0));
//            log.info("userTags(0):{}",tags.get(0).get(0).getType());

            // 2-5. 태그별 빈도수로 내림차순 정렬
            List<String> keySet = new ArrayList<>(userTags.keySet());
            keySet.sort((o1,o2) -> userTags.get(o2).compareTo(userTags.get(o1))); //value값으로 내림차순 정렬

            // 2-6. 빈도수 많은 순으로 태그 리스트에 담음
            List<String> sortedTagIds = new ArrayList<>(); //담을 리스트
            Map<String, Integer> sortedTags = new HashMap<>();
            int count = keySet.size()>6 ? 6 : keySet.size();
            for (int i=0; i<count; i++) {
                String key = keySet.get(i);
                sortedTagIds.add(key); //태그명
                sortedTags.put(key,userTags.get(key)); //태그명, 빈도수
            }

            // 3. 클라이언트에 sortedTagIds를 전송
//            rabbitService.sendMessage(exchange, routingKey, matchingRequest);
            messagingService.sendTagsToAll(sortedTagIds,sortedTags);
        }
    }
}
