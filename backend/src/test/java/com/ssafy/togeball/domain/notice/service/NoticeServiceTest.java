package com.ssafy.togeball.domain.notice.service;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.notice.dto.NoticeResponse;
import com.ssafy.togeball.domain.notice.dto.NoticesResponse;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.notice.repository.EmitterRepository;
import com.ssafy.togeball.domain.notice.repository.NoticeRepository;
import com.ssafy.togeball.domain.user.dto.UserResponse;
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
        userRepository.save(loginUser);

        matching1 = Matching.builder()
                .matchingChatroom(matchingChatroom1)
                .title("매칭 제목 1")
                .capacity(10)
                .build();
        matchingRepository.save(matching1);

        matching2 = Matching.builder()
                .matchingChatroom(matchingChatroom2)
                .title("매칭 제목 2")
                .capacity(10)
                .build();
        matchingRepository.save(matching2);

        matchingChatroom1 = MatchingChatroom.builder()
                .title("매칭 채팅방 제목 1")
                .capacity(2)
                .build();
        matchingChatroom1.setMatching(matching1);
        chatroomRepository.save(matchingChatroom1);

        matchingChatroom2 = MatchingChatroom.builder()
                .title("매칭 채팅방 제목 2")
                .capacity(2)
                .build();
        matchingChatroom2.setMatching(matching2);
        chatroomRepository.save(matchingChatroom2);
    }

    private List<Notice> noticeList() {

        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(new Notice(loginUser, matching1, false));
        noticeList.add(new Notice(loginUser, matching2, false));
        return noticeList;
    }

    @DisplayName("구독 성공")
    @Test
    void subscribeTest() {

        // Given
        User loginUser = User.builder().build();
        ReflectionTestUtils.setField(loginUser, "id", 1); //User 객체에 id 설정
        String lastEventId = "lastEventId";
        when(emitterRepository.save(any(), any())).thenReturn(new SseEmitter());
        when(emitterRepository.findAllEventCacheStartWithId(any())).thenReturn(Collections.emptyMap());

        // When
        SseEmitter result = noticeService.subscribe(loginUser.getId(), lastEventId);

        // Then
        verify(emitterRepository, times(1)).save(any(), any());
        verify(emitterRepository, times(1)).findAllEventCacheStartWithId(any());
    }

//    @DisplayName("알림 객체 생성, 저장, 보내기 성공")
//    @Test
//    void sendTest() {
//
//        // Given
//        MatchingResponse matchingResponse = Mockito.mock(MatchingResponse.class);
////        List<UserResponse> receivers = List.of(Mockito.mock(UserResponse.class));
//        ReflectionTestUtils.setField(matchingResponse, "id", 1);
//        User user = Mockito.mock(User.class);
//        UserResponse userResponse = Mockito.mock(UserResponse.class);
//        ReflectionTestUtils.setField(userResponse, "id", 1);
//        Matching matching = Mockito.mock(Matching.class);
//
//        List<UserResponse> receivers = new ArrayList<>();
//        receivers.add(userResponse);
//        when(matchingResponse.getUsers()).thenReturn(receivers);
//        when(userRepository.findById(receivers.get(0).getId())).thenReturn(Optional.of(user));
//        when(matchingRepository.findById(matchingResponse.getId())).thenReturn(Optional.of(matching));
//        when(noticeRepository.save(any())).thenReturn(Mockito.mock(Notice.class));
//        when(emitterRepository.findAllStartWithById(any())).thenReturn(Collections.singletonMap("1", mock(SseEmitter.class)));
//
//        // When
//        noticeService.send(matchingResponse);
//
//        // Then
//        verify(userRepository, times(1)).findById(userResponse.getId());
//        verify(matchingRepository, times(1)).findById(any());
//        verify(noticeRepository, times(1)).save(any());
//        verify(emitterRepository, times(1)).findAllStartWithById(any());
//        verify(emitterRepository, times(1)).saveEventCache(any(), any());
//    }

//    @DisplayName("유저 번호로 알림 목록 조회 성공")
//    @Test
//    void findAllByUserIdTest() {
//
//        // Given
//        dataInit();
//        ReflectionTestUtils.setField(loginUser, "id", 1);
//        when(noticeRepository.findAllByUserId(loginUser.getId())).thenReturn(noticeList());
//
//        // When
//        NoticesResponse noticesResponse = noticeService.findAllByUserId(loginUser.getId());
//
//        // Then
//        verify(noticeRepository, times(loginUser.getId())).findAllByUserId(1);
//        assertEquals(2, noticesResponse.getNoticeResponses().size());
//        assertEquals(2, noticesResponse.getUnreadCount());
//    }

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
