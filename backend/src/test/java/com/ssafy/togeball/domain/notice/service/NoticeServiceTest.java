package com.ssafy.togeball.domain.notice.service;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.notice.dto.NoticeResponse;
import com.ssafy.togeball.domain.notice.dto.NoticesResponse;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.notice.repository.EmitterRepository;
import com.ssafy.togeball.domain.notice.repository.NoticeRepository;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatroomRepository chatroomRepository;
    @Mock
    private MatchingRepository matchingRepository;
    @Mock
    private EmitterRepository emitterRepository;
    @Mock
    private NoticeRepository noticeRepository;
    @InjectMocks
    private NoticeService noticeService;

    private User loginUser;
    private MatchingChatroom matchingChatroom1;
    private MatchingChatroom matchingChatroom2;
    private Matching matching1;
    private Matching matching2;

    void dataInit() {
        loginUser = User.builder()
                .email("email@gmail.com")
                .nickname("닉네임")
                .birthdate(LocalDateTime.of(1999, 4, 23, 0, 0, 0))
                .gender(Gender.FEMALE)
                .phone("010-1234-5678")
                .profileImage("profile.jpg")
                .build();

        matching1 = Matching.builder()
                .matchingChatroom(matchingChatroom1)
                .title("매칭 제목 1")
                .capacity(10)
                .build();

        matching2 = Matching.builder()
                .matchingChatroom(matchingChatroom2)
                .title("매칭 제목 2")
                .capacity(10)
                .build();

        matchingChatroom1 = MatchingChatroom.builder()
                .matching(matching1)
                .title("매칭 채팅방 제목 1")
                .build();

        matchingChatroom2 = MatchingChatroom.builder()
                .matching(matching2)
                .title("매칭 채팅방 제목 2")
                .build();
    }

    @DisplayName("구독 성공")
    @Test
    void subscribeTest() {

        // Given
        User loginUser = User.builder().build();
        // ReflectionTestUtils를 사용하여 User 객체에 id 설정
        ReflectionTestUtils.setField(loginUser, "id", 1);
        String lastEventId = "lastEventId";
        when(emitterRepository.save(any(), any())).thenReturn(new SseEmitter());
        when(emitterRepository.findAllEventCacheStartWithId(any())).thenReturn(Collections.emptyMap());

        // When
        SseEmitter result = noticeService.subscribe(loginUser, lastEventId);

        // Then
        verify(emitterRepository, times(1)).save(any(), any());
        verify(emitterRepository, times(1)).findAllEventCacheStartWithId(any());
    }

    @DisplayName("알림 보내기 성공")
    @Test
    void sendToClientTest() throws IOException {

//        // Given
//        SseEmitter emitter = mock(SseEmitter.class);
//        String id = "1_1234";
//        String name = "sseTest";
//        Object data = "sendToClientTest를 위한 데이터";
//
//        when(emitter.send(any())).thenReturn(emitter);
//
//        // When
//        noticeService.sendToClient(emitter, id, data);
//
//        // Then
//        verify(emitter, times(1)).send(event()
//                .id(id)
//                .name("sse")
//                .data(data));
    }

    @DisplayName("알림 객체 생성해서 send 메소드 호출 성공")
    @Test
    void sendTest() {
//        // Given
//        MatchingResponse matchingResponse = new MatchingResponse();
//        UserResponse userResponse = new UserResponse();
//        userResponse.setId(1);
//        matchingResponse.setUsers(Collections.singletonList(userResponse));
//
//        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(new User()));
//        when(matchingRepository.findById(any())).thenReturn(java.util.Optional.of(new Matching()));
//        when(emitterRepository.findAllStartWithById(any())).thenReturn(Collections.singletonMap("1", mock(SseEmitter.class)));
//
//        // When
//        noticeService.send(matchingResponse);
//
//        // Then
//        // Verify that userRepository.findById and matchingRepository.findById are called
//        verify(userRepository, times(1)).findById(1);
//        verify(matchingRepository, times(1)).findById(any());
//
//        // Verify that noticeRepository.save is called
//        verify(noticeRepository, times(1)).save(any());
//
//        // Verify that emitterRepository.findAllStartWithById is called
//        verify(emitterRepository, times(1)).findAllStartWithById(any());
//
//        // Verify that emitterRepository.saveEventCache and sendToClient are called for each emitter
//        verify(emitterRepository, times(1)).saveEventCache(any(), any());
//        verify(noticeService, times(1)).sendToClient(any(), any(), any());
//
//        // Add more assertions as needed
    }

    @DisplayName("유저 번호로 알림 목록 조회 성공")
    @Test
    void findAllByUserIdTest() {

        // Given
        dataInit();
        ReflectionTestUtils.setField(loginUser, "id", 1);
        doReturn(noticeList().stream().map(NoticeResponse::of).collect(Collectors.toList())).when(noticeRepository).findAllByUserId(1);

        // When
        NoticesResponse noticesResponse = noticeService.findAllByUserId(loginUser);

        // Then
//        verify(noticeRepository, times(1)).findAllByUserId(1);
//        verify(noticeResponse, times(1)).of(notice1);
//        verify(noticeResponse, times(1)).of(notice2);
//        assertEquals(2, noticesResponse.getNoticeResponses().size());
//        assertEquals(2, noticesResponse.getUnreadCount());

        //    public NoticesResponse findAllByUserId(User loginUser) {
//        List<NoticeResponse> responses = noticeRepository.findAllByUserId(loginUser.getId()).stream()
//                .map(NoticeResponse::of)
//                .collect(Collectors.toList());
//        long unreadCount = responses.stream()
//                .filter(notice -> !notice.isRead())
//                .count();
//        return NoticesResponse.of(responses, unreadCount);
    }

    private List<Notice> noticeList() {
        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(new Notice(loginUser, matching1, false));
        noticeList.add(new Notice(loginUser, matching2, false));
        return noticeList;
    }

    private List<NoticeResponse> noticeResponseList() {
        List<NoticeResponse> noticeResponseList = new ArrayList<>();
        noticeResponseList.add(new NoticeResponse(1,"매칭 제목 1",LocalDateTime.now(),false));
        noticeResponseList.add(new NoticeResponse(2,"매칭 제목 2",LocalDateTime.now(),false));
        return noticeResponseList;
    }



    @DisplayName("알림 읽음 표시 성공")
    @Test
    void readNoticeTest() {

        // Given
        Integer noticeId = 1;
        Notice notice = Mockito.mock(Notice.class);
        ReflectionTestUtils.setField(notice, "id", noticeId);
        ReflectionTestUtils.setField(notice, "isRead", false);
        when(noticeRepository.findById(1)).thenReturn(Optional.of(notice));

        // When
        noticeService.readNotice(noticeId);

        // Then
        verify(noticeRepository, times(1)).findById(noticeId);
        verify(notice, times(1)).read();
    }

    @DisplayName("존재하지 않는 알림 읽기 시도 시 익셉션 성공")
    @Test
    public void testReadNoticeWithNonexistentId() {

        // Given
        Integer nonexistentId = 99;
        when(noticeRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        // When and Then
        // Ensure that EntityNotFoundException is thrown when noticeRepository.findById returns empty
        assertThrows(EntityNotFoundException.class, () -> noticeService.readNotice(nonexistentId));
        verify(noticeRepository, times(1)).findById(nonexistentId);
    }
}
