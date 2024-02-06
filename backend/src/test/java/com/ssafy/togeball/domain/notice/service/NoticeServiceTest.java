package com.ssafy.togeball.domain.notice.service;

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
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    void findAllByUserIdTest() {

        // Given
        User loginUser = Mockito.mock(User.class);
        ReflectionTestUtils.setField(loginUser, "id", 1);
        Matching matching1 = Mockito.mock(Matching.class);
        ReflectionTestUtils.setField(matching1, "id", 1);
        ReflectionTestUtils.setField(matching1, "title", "matching title 1");
        Matching matching2 = Mockito.mock(Matching.class);
        ReflectionTestUtils.setField(matching2, "id", 2);
        ReflectionTestUtils.setField(matching2, "title", "matching title 2");
        Notice notice1 = Mockito.mock(Notice.class);
        Notice notice2 = Mockito.mock(Notice.class);
        ReflectionTestUtils.setField(loginUser, "id", 1);
        ReflectionTestUtils.setField(notice1, "user", loginUser);
        ReflectionTestUtils.setField(notice2, "user", loginUser);
        ReflectionTestUtils.setField(notice1, "matching", matching1);
        ReflectionTestUtils.setField(notice2, "matching", matching2);
        when(noticeRepository.findAllByUserId(loginUser.getId())).thenReturn(Arrays.asList(notice1, notice2));
        NoticeResponse noticeResponse = Mockito.mock(NoticeResponse.class);


        // When
        NoticesResponse noticesResponse = noticeService.findAllByUserId(loginUser);

        // Then
        verify(noticeRepository, times(1)).findAllByUserId(1);
        verify(noticeResponse, times(1)).of(notice1);
        verify(noticeResponse, times(1)).of(notice2);
        assertEquals(2, noticesResponse.getNoticeResponses().size());
        assertEquals(2, noticesResponse.getUnreadCount());
    }

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
