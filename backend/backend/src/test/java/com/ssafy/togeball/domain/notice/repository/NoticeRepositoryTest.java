package com.ssafy.togeball.domain.notice.repository;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class NoticeRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatroomRepository chatroomRepository;

    @Autowired
    MatchingRepository matchingRepository;

    @Autowired
    NoticeRepository noticeRepository;

    private User user;

    private MatchingChatroom matchingChatroom;

    private Matching matching;

    void dataInit() {

        user = User.builder()
            .email("aycho3030@gmail.com")
            .nickname("아영")
            .gender(Gender.FEMALE)
            .birthdate(LocalDateTime.of(1994,4,30,0,0,0))
            .phone("010-1000-2000")
            .profileImage("profile1.png")
            .build();
        userRepository.save(user);

        matchingChatroom = MatchingChatroom.builder()
            .title("매칭 채팅방 재목 1")
            .build();
        chatroomRepository.save(matchingChatroom);

        matching = Matching.builder()
            .matchingChatroom(matchingChatroom)
            .title("매칭 제목 1")
            .capacity(10)
            .build();
        matchingRepository.save(matching);

        Notice notice = Notice.builder()
                .user(user)
                .matching(matching)
                .isRead(false)
                .build();
        noticeRepository.save(notice);
    }

    @Test
    void noticeSaveTest() {

        User user1 = User.builder()
                .email("aycho3030@gmail.com")
                .nickname("아영")
                .gender(Gender.FEMALE)
                .birthdate(LocalDateTime.of(1994,4,30,0,0,0))
                .phone("010-1000-2000")
                .profileImage("profile1.png")
                .build();
        userRepository.save(user1);

        MatchingChatroom matchingChatroom1 = MatchingChatroom.builder()
                .title("매칭 채팅방 제목 1")
                .build();
        chatroomRepository.save(matchingChatroom1);

        Matching matching1 = Matching.builder()
                .matchingChatroom(matchingChatroom1)
                .title("매칭 제목 1")
                .capacity(10)
                .build();
        matchingRepository.save(matching1);

        Notice notice1 = Notice.builder()
                .user(user1)
                .matching(matching1)
                .isRead(false)
                .build();
        Notice saved = noticeRepository.save(notice1);

        assertNotNull(saved.getUser().getId());
        assertEquals("매칭 채팅방 제목 1", saved.getMatching().getMatchingChatroom().getTitle());
        assertEquals("매칭 제목 1", saved.getMatching().getTitle());
    }

    @Test
    void noticeFindAllByUserIdTest() {

        // Given
        dataInit();
        User saveUser1 = userRepository.save(user);

        // When
        List<Notice> noticesFindAllByUserId = noticeRepository.findAllByUserId(saveUser1.getId());

        // Then
        assertEquals(1, noticesFindAllByUserId.size());
    }

    @Test
    void noticeDuplicateMatchingTest() {

    }
}
