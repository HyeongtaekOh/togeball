package com.ssafy.togeball.domain.notice.facade;

import com.ssafy.togeball.domain.matching.dto.MatchingRequest;
import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.entity.MatchingUser;
import com.ssafy.togeball.domain.matching.service.MatchingService;
import com.ssafy.togeball.domain.notice.dto.NoticeResponse;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.notice.service.NoticeService;
import com.ssafy.togeball.domain.notice.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingNoticeFacade {

    private final SseService sseService;
    private final NoticeService noticeService;
    private final MatchingService matchingService;

    @Transactional
//    @RabbitListener(queues = "${rabbitmq.notification.matching.queue}")
    public MatchingResponse sendMatchingNotification(MatchingRequest matchingRequest) {
        Matching matchingResult = matchingService.createMatchingAndChatroom(matchingRequest);
        for (MatchingUser matchingUser : matchingResult.getMatchingUsers()) {
            Notice notice = Notice.builder()
                    .user(matchingUser.getUser())
                    .matching(matchingResult)
                    .isRead(false)
                    .build();
            NoticeResponse message = noticeService.save(notice);
            log.info("send matching notice to user: {}", matchingUser.getUser().getId());
            sseService.sendToUser(matchingUser.getUser().getId(), "matching", message);
        }

        return MatchingResponse.of(matchingResult);
    }
}
