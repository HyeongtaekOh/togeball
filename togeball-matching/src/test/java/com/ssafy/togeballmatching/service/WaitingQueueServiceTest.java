package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingUser;
import com.ssafy.togeballmatching.dto.UserTag;
import com.ssafy.togeballmatching.service.queue.WaitingQueueService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WaitingQueueServiceTest {

//    WaitingQueueService service = new MemoryWaitingQueueService();
    @Autowired
WaitingQueueService service;

    static UserTag userTag1;
    static UserTag userTag2;
    static UserTag userTag3;
    static UserTag userTag4;
    static UserTag userTag5;
    static UserTag userTag6;
    static MatchingUser matchingUser1;
    static MatchingUser matchingUser2;
    static MatchingUser matchingUser3;
    static MatchingUser matchingUser4;
    static MatchingUser matchingUser5;
    static MatchingUser matchingUser6;
    static MatchingUser matchingUser7;

    @BeforeAll
    static void setUp() {
        userTag1 = UserTag.builder()
                .tagId(1)
                .content("맥주형")
                .tagType(UserTag.TagType.CHEERING_STYLE)
                .build();
        userTag2 = UserTag.builder()
                .tagId(2)
                .content("SSG")
                .tagType(UserTag.TagType.PREFERRED_TEAM)
                .build();
        userTag3 = UserTag.builder()
                .tagId(3)
                .content("SSG랜더스필드")
                .tagType(UserTag.TagType.PREFERRED_STADIUM)
                .build();
        userTag4 = UserTag.builder()
                .tagId(4)
                .content("응원지정석")
                .tagType(UserTag.TagType.PREFERRED_SEAT)
                .build();
        userTag5 = UserTag.builder()
                .tagId(5)
                .content("ENFP")
                .tagType(UserTag.TagType.MBTI)
                .build();
        userTag6 = UserTag.builder()
                .tagId(6)
                .content("남자만")
                .tagType(UserTag.TagType.CUSTOM)
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

        service.addQueue(matchingUser1);

        List<MatchingUser> waitingUsers = service.getFirstNWaitingUsers(1);
        log.info("waitingUsers: {}", waitingUsers);
        assertEquals(1, waitingUsers.size());
        assertEquals(matchingUser1, waitingUsers.get(0));
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
        List<MatchingUser> matchingUsers = List.of(matchingUser1, matchingUser2, matchingUser3, matchingUser4, matchingUser5, matchingUser6, matchingUser7);
        assertEquals(matchingUsers, waitingUsers);
    }

    @Test
    void getFirstNWaitingUsers() {

        service.addQueue(matchingUser1);
        service.addQueue(matchingUser2);
        service.addQueue(matchingUser3);
        service.addQueue(matchingUser4);
        service.addQueue(matchingUser5);
        service.addQueue(matchingUser6);
        service.addQueue(matchingUser7);

        List<MatchingUser> firstNWaitingUsers = service.getFirstNWaitingUsers(3);

        log.info("firstNWaitingUsers: {}", firstNWaitingUsers);
        List<MatchingUser> first3MatchingUsers = List.of(matchingUser1, matchingUser2, matchingUser3);
        assertEquals(first3MatchingUsers, firstNWaitingUsers);
    }

    @Test
    void removeFirstNQueue() {

        service.addQueue(matchingUser1);
        service.addQueue(matchingUser2);
        service.addQueue(matchingUser3);
        service.addQueue(matchingUser4);
        service.addQueue(matchingUser5);
        service.addQueue(matchingUser6);
        service.addQueue(matchingUser7);

        service.removeFirstNQueue(3);

        List<MatchingUser> remainingMatchingUsers = List.of(matchingUser4, matchingUser5, matchingUser6, matchingUser7);
        List<MatchingUser> waitingUsers = service.getWaitingUsers();
        log.info("waitingUsers: {}", waitingUsers);
        assertEquals(remainingMatchingUsers, waitingUsers);
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