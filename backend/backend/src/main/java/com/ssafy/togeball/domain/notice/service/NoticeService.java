package com.ssafy.togeball.domain.notice.service;

import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.notice.dto.NoticeResponse;
import com.ssafy.togeball.domain.notice.dto.NoticesResponse;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.notice.repository.EmitterRepository;
import com.ssafy.togeball.domain.notice.repository.NoticeRepository;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; //1시간

    private final UserRepository userRepository;

    private final MatchingRepository matchingRepository;

    private final NoticeRepository noticeRepository;

    private final EmitterRepository emitterRepository;


    public NoticeResponse save(Notice notice) {
        return NoticeResponse.of(noticeRepository.save(notice));
    }

    public SseEmitter subscribe(Integer userId, String lastEventId) {

        String id = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]"); // 503 에러를 방지하기 위한 더미 이벤트 전송
        if (!lastEventId.isEmpty()) { // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
            System.out.println("sendToClient id:"+id);
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    @Transactional
    public void send(MatchingResponse matchingResponse) {
        List<UserResponse> receivers = matchingResponse.getUsers(); //알림 보낼 유저들
        receivers.forEach(
                (receiver) -> {
                    String id = String.valueOf(receiver.getId());
                    Notice notice = Notice.builder() //각 유저마다 알림 객체 생성
                            .user(userRepository.findById(receiver.getId())
                                    .orElseThrow(EntityNotFoundException::new))
                            .matching(matchingRepository.findById(matchingResponse.getId())
                                    .orElseThrow(EntityNotFoundException::new))
                            .isRead(false)
                            .build();
                    noticeRepository.save(notice); //알림 객체 저장
//                    Notice savedNotice = noticeRepository.save(notice);
//                    System.out.println(savedNotice.getId());
                    Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
                    sseEmitters.forEach(
                            (key, emitter) -> {
                                emitterRepository.saveEventCache(key, notice);
                                sendToClient(emitter, key, NoticeResponse.of(notice));
                            }
                    );
                }
        );
    }

    @Transactional
    public NoticesResponse findAllByUserId(Integer userId) {
        List<NoticeResponse> responses = noticeRepository.findAllByUserId(userId).stream()
                .map(NoticeResponse::of)
                .collect(Collectors.toList());
        long unreadCount = responses.stream()
                .filter(notice -> !notice.isRead())
                .count();
        return NoticesResponse.of(responses, unreadCount);
    }

    @Transactional
    public void readNotice(Integer id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 알림입니다."));
        notice.read();
    }
}
