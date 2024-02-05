package com.ssafy.togeball.domain.notice.dto;

import com.ssafy.togeball.domain.notice.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeResponse {

    private Integer id; //알림 아이디
    private String matchingTitle; //매칭 타이틀
    private LocalDateTime regdate; //알림이 생성된 날짜
    private boolean isRead; //읽음 여부

    @Builder
    public NoticeResponse(Integer id, String matchingTitle, LocalDateTime regdate, boolean isRead) {
        this.id = id;
        this.matchingTitle = matchingTitle;
        this.regdate = regdate;
        this.isRead = isRead;
    }

    public static NoticeResponse of(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .matchingTitle(notice.getMatching().getTitle())
                .regdate(notice.getRegdate())
                .isRead(notice.isRead())
                .build();
    }
}
