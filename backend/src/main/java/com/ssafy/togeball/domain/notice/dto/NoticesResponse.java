package com.ssafy.togeball.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticesResponse {

    private List<NoticeResponse> noticeResponses; //로그인한 유저의 모든 알림

    private long unreadCount; //로그인한 유저의 읽지 않은 알림 수

    @Builder
    public NoticesResponse(List<NoticeResponse> noticeResponses, long unreadCount) {
        this.noticeResponses = noticeResponses;
        this.unreadCount = unreadCount;
    }

    public static NoticesResponse of(List<NoticeResponse> noticeResponses, long count) {
        return NoticesResponse.builder()
                .noticeResponses(noticeResponses)
                .unreadCount(count)
                .build();
    }
}