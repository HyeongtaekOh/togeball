package com.ssafy.togeballmatching.scheduler;

import com.ssafy.togeballmatching.dto.MatchingRequest;
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

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.matching.routing-key}")
    private String routingKey;

    private final RabbitMQService rabbitService;
    private final MessagingService messagingService;
    private final WaitingQueueService waitingQueueService;
    private final WebSocketSessionStoreService webSocketSessionStoreService;

    @Scheduled(fixedDelay = 10000)
    public void matching() {

        List<WebSocketSession> sessions = webSocketSessionStoreService.getAllWebSocketSession();
        List<MatchingUser> waitingUsers = waitingQueueService.getWaitingUsers();

        /**
         * TODO: 매칭 알고리즘 구현
         * 1. 대기 중인 사용자가 2명 이상일 때, 매칭을 시도한다.
         * 2. 매칭 알고리즘을 수행한다.
         * 3. API 서버에 매칭 결과를 전송한다.
         * 4. 매칭된 사용자들에게 매칭 결과를 전송한다.
         */


        // 1. 대기 중인 사용자가 2명 이상일 때, 매칭을 시도한다.
        if (sessions.size() >= 2) {

            // 2. 매칭 알고리즘을 수행한다. (알고리즘 미구현으로 여기서는 대기 중인 사용자들 중 2명을 매칭시킴)

            // 2-1. 유저 목록 받아옴
            List<Integer> userIds = sessions.stream().map(session -> (Integer) session.getAttributes().get("userId")).toList();

            //TODO: 그냥 매칭 생략하고 현재 대기 중인 사용자 전체를 한번에 매칭시켜도 좋을 듯? 테스트 인원도 소규모일 예정이니

            // 2-2. 유저별 태그 받을 Set과 List
            List<List<Tag>> tags = new ArrayList<>();

            // 2-3. 태그별 빈도수 카운트할 HashMap
            Map<String, Integer> userTags = new HashMap<>();

            // 2-4. 태그 받아옴
            for (int i=0; i<userIds.size(); i++) {
                List<Tag> tag = (ArrayList) sessions.get(i).getAttributes().get("tags");
                for (int j=0; j<tag.size(); j++) {
                    String content = tag.get(i).getContent();
                    if (userTags.containsKey(content)) userTags.put(content,userTags.get(content)+1);
                    else userTags.put(content, 0);
                }
                tags.add(tag);
            }

            log.info("userId(0):{}",userIds.get(0));
            log.info("userTags(0):{}",tags.get(0).get(0).getType());

            // 2-5. 태그별 빈도수로 내림차순 정렬
            List<String> keySet = new ArrayList<>(userTags.keySet());
            keySet.sort((o1,o2) -> userTags.get(o2).compareTo(userTags.get(o1))); //value값으로 내림차순 정렬

            // 2-6. 빈도수 많은 순으로 최대 10개 출력
            int tagCount = keySet.size()>=10 ? 10 : keySet.size();
            for (int i=0; i<tagCount; i++) {
                String key = keySet.get(i);
                System.out.print("Key: " + key); //태그명
                System.out.println(", Val: " + userTags.get(key)); //빈도수
            }

            // 3. API 서버에 모든 매칭 결과를 전송한다. (MatchingRequest 객체를 생성하여 RabbitMQ를 통해 API 서버에 전송)
            //    전송된 매칭 결과는 API 서버에 의해 DB에 저장된다.
            MatchingRequest matchingRequest = MatchingRequest.builder()
                    .title(LocalDateTime.now().toString()+"에 이루어진 매칭입니다.")
                    .capacity(userIds.size())
                    .userIds(userIds)
                    .tagIds(List.of(1,2,3))  // TODO: 매칭된 유저의 태그 ID 목록을 가져와 설정한다. (중복 제거 필수)
                    .build();
            rabbitService.sendMessage(exchange, routingKey, matchingRequest);
            messagingService.sendMatchingResultToUsers(userIds.subList(0, 2));
        }
    }
}
