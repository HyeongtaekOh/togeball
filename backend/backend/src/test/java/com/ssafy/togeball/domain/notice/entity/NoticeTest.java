package com.ssafy.togeball.domain.notice.entity;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class NoticeTest {

    @Test
    void noticeBuilderTest() {
        //Given
        User user = User.builder()
                .email("aycho3030@gmail.com")
                .nickname("아영")
                .gender(Gender.FEMALE)
                .birthdate(LocalDateTime.of(1994,4,30,0,0,0))
                .phone("010-1000-2000")
                .profileImage("profile1.png")
                .build();

        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title("title")
                .build();

        Matching matching = Matching.builder()
                .matchingChatroom(matchingChatroom)
                .title("title")
                .capacity(10)
                .build();

        //When
        Notice notice = Notice.builder()
                .user(user)
                .matching(matching)
                .isRead(false)
                .build();

        //Then
        System.out.println("User: " + notice.getUser().getNickname());
        System.out.println("Matching: " + notice.getMatching().getTitle());
    }
}
