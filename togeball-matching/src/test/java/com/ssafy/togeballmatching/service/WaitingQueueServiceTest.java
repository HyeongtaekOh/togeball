package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.dto.TagType;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WaitingQueueServiceTest {

//    WaitingQueueService service = new MemoryWaitingQueueService();
    @Autowired WaitingQueueService service;

    static Tag userTag1;
    static Tag userTag2;
    static Tag userTag3;
    static Tag userTag4;
    static Tag userTag5;
    static Tag userTag6;
    static MatchingUser matchingUser1;
    static MatchingUser matchingUser2;
    static MatchingUser matchingUser3;
    static MatchingUser matchingUser4;
    static MatchingUser matchingUser5;
    static MatchingUser matchingUser6;
    static MatchingUser matchingUser7;

    @BeforeAll
    static void setUp() {
        userTag1 = Tag.builder()
                .id(1)
                .content("맥주형")
                .type(TagType.CHEERING_STYLE)
                .build();
        userTag2 = Tag.builder()
                .id(2)
                .content("SSG")
                .type(TagType.PREFERRED_TEAM)
                .build();
        userTag3 = Tag.builder()
                .id(3)
                .content("SSG랜더스필드")
                .type(TagType.PREFERRED_STADIUM)
                .build();
        userTag4 = Tag.builder()
                .id(4)
                .content("응원지정석")
                .type(TagType.PREFERRED_SEAT)
                .build();
        userTag5 = Tag.builder()
                .id(5)
                .content("ENFP")
                .type(TagType.MBTI)
                .build();
        userTag6 = Tag.builder()
                .id(6)
                .content("남자만")
                .type(TagType.CUSTOM)
                .build();


        matchingUser1 = MatchingUser.builder()
                .userId(1)
                .nickname("user1")
                .profileImage("user1.jpg")
                .tags(List.of(userTag1, userTag2, userTag3, userTag4))
                .build();
        matchingUser2 = MatchingUser.builder()
                .userId(2)
                .nickname("user2")
                .profileImage("user2.jpg")
                .tags(List.of(userTag1, userTag2, userTag4, userTag6))
                .build();
        matchingUser3 = MatchingUser.builder()
                .userId(3)
                .nickname("user3")
                .profileImage("user3.jpg")
                .tags(List.of(userTag1, userTag2, userTag3, userTag5))
                .build();
        matchingUser4 = MatchingUser.builder()
                .userId(4)
                .nickname("user4")
                .profileImage("user4.jpg")
                .tags(List.of(userTag3, userTag4, userTag5, userTag6))
                .build();
        matchingUser5 = MatchingUser.builder()
                .userId(5)
                .nickname("user5")
                .profileImage("user5.jpg")
                .tags(List.of(userTag2, userTag3, userTag4, userTag5))
                .build();
        matchingUser6 = MatchingUser.builder()
                .userId(6)
                .nickname("user6")
                .profileImage("user6.jpg")
                .tags(List.of(userTag2, userTag3, userTag5, userTag6))
                .build();
        matchingUser7 = MatchingUser.builder()
                .userId(7)
                .nickname("user7")
                .profileImage("user7.jpg")
                .tags(List.of(userTag1, userTag4, userTag5, userTag6))
                .build();
    }

    @AfterEach
    void tearDown() {
        service.clearQueue();
    }

    @Test
    void addQueue() {
        service.clearQueue();

        service.addQueue(matchingUser1);

        MatchingUser waitingUser = service.getWaitingUsers().get(0);
        log.info("waitingUser: {}", waitingUser);
        assertEquals(matchingUser1, waitingUser);
    }

    @Test
    void getWaitingUsers() {

        service.addQueue(matchingUser1);
        service.addQueue(matchingUser2);
        service.addQueue(matchingUser3);
        service.addQueue(matchingUser4);
        service.addQueue(matchingUser5);
        service.addQueue(matchingUser6);
        service.addQueue(matchingUser7);

        List<MatchingUser> waitingUsers = service.getWaitingUsers();
        log.info("waitingUsers: {}", waitingUsers);

        List<Integer> matchingUserIds = Stream.of(matchingUser1, matchingUser2, matchingUser3, matchingUser4, matchingUser5, matchingUser6, matchingUser7).map(MatchingUser::getUserId).toList();
        waitingUsers.forEach(matchingUser -> assertTrue(matchingUserIds.contains(matchingUser.getUserId())));
    }

    @Test
    void clearQueue() {

        service.addQueue(matchingUser1);
        service.addQueue(matchingUser2);
        service.addQueue(matchingUser3);
        service.addQueue(matchingUser4);
        service.addQueue(matchingUser5);
        service.addQueue(matchingUser6);
        service.addQueue(matchingUser7);

        service.clearQueue();

        List<MatchingUser> waitingUsers = service.getWaitingUsers();
        log.info("waitingUsers: {}", waitingUsers);
        assertEquals(0, waitingUsers.size());
    }
}