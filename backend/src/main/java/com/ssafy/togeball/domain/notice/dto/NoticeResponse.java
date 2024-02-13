package com.ssafy.togeball.domain.notice.dto;

import com.ssafy.togeball.domain.notice.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponse {

    private Integer id; //알림 아이디
    private String matchingTitle; //매칭 타이틀
    private Integer matchingChatroomId; //매칭 채팅방 아이디
    private LocalDateTime regdate; //알림이 생성된 날짜
    private boolean isRead; //읽음 여부

    public static NoticeResponse of(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .matchingTitle(notice.getMatching().getTitle())
                .matchingChatroomId(notice.getMatching().getMatchingChatroom().getId())
                .regdate(notice.getRegdate())
                .isRead(notice.isRead())
                .build();
    }
}
