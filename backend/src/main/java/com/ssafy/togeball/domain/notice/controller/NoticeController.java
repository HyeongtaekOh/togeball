package com.ssafy.togeball.domain.notice.controller;

import com.ssafy.togeball.domain.notice.dto.NoticeResponse;
import com.ssafy.togeball.domain.notice.dto.NoticesResponse;
import com.ssafy.togeball.domain.notice.service.NoticeService;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(User loginUser,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return noticeService.subscribe(loginUser, lastEventId);
    }

    //로그인 유저의 모든 알림 조회
    @GetMapping("/api/notices")
    public ResponseEntity<NoticesResponse> notices(User loginUser) {
        return ResponseEntity.ok().body(noticeService.findAllByUserId(loginUser));
    }

    //알림 읽음 상태 변경
    @PatchMapping("/api/notice/{id}")
    public ResponseEntity<Void> readNotice(@PathVariable Integer id) {
        noticeService.readNotice(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
