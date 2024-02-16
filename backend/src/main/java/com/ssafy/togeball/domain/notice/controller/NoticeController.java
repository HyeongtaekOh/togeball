package com.ssafy.togeball.domain.notice.controller;

import com.ssafy.togeball.domain.auth.aop.UserContext;
import com.ssafy.togeball.domain.notice.dto.NoticesResponse;
import com.ssafy.togeball.domain.notice.service.NoticeService;
import com.ssafy.togeball.domain.notice.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final SseService sseService;
    private final NoticeService noticeService;

    @UserContext
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(Integer userId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return noticeService.subscribe(userId, lastEventId);
    }

    @UserContext
    @GetMapping(value = "/sse/notification/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Integer userId) {
        return ResponseEntity.ok(sseService.subscribe(userId));
    }

    //로그인 유저의 모든 알림 조회
    @UserContext
    @GetMapping("/api/notices")
    public ResponseEntity<NoticesResponse> notices(Integer userId) {
        return ResponseEntity.ok().body(noticeService.findAllByUserId(userId));
    }

    //알림 읽음 상태 변경
    @PatchMapping("/api/notices/{id}")
    public ResponseEntity<Void> readNotice(@PathVariable Integer id) {
        noticeService.readNotice(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
