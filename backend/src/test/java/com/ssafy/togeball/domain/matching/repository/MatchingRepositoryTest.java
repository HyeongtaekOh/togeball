package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.matching.dto.MatchingCreateDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
public class MatchingRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MatchingRepository matchingRepository;

    private User user1;
    private User user2;
    private User user3;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Tag tag4;

    @BeforeEach
    void setUp() {
        dataInit();
    }

    void dataInit() {

        user1 = User.builder()
                .email("test@TEST")
                .nickname("nickname")
                .password("password")
                .build();
        user2 = User.builder()
                .email("test@TES2T")
                .nickname("nickname2")
                .password("password")
                .build();
        user3 = User.builder()
                .email("test@TES3T")
                .nickname("nickname3")
                .password("password")
                .build();


        tag1 = Tag.builder()
                .type(TagType.CHEERING_STYLE)
                .content("응원가형")
                .build();
        tag2 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("SSG")
                .build();
        tag3 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("기아")
                .build();
        tag4 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("삼성")
                .build();

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        em.persist(tag4);
        em.flush();
        em.clear();
    }

    @Test
    void createMatchingIntegrityError() {
        List<Integer> userIds = List.of(user1.getId(), user2.getId(), user3.getId(), 999);          // 999는 존재하지 않는 유저
        List<Integer> tagIds = List.of(tag1.getId(), tag2.getId(), tag3.getId(), tag4.getId(), 10); // 10은 존재하지 않는 태그

        MatchingCreateDto matchingCreateDto = MatchingCreateDto.builder()
                .title("test")
                .capacity(10)
                .userIds(userIds)
                .tagIds(tagIds)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            matchingRepository.createMatchingAndChatroom(matchingCreateDto);
        });
    }

    @Test
    void createMatchingAndChatroom() {
        List<Integer> userIds = List.of(user1.getId(), user2.getId(), user3.getId());
        List<Integer> tagIds = List.of(tag1.getId(), tag2.getId(), tag3.getId(), tag4.getId());

        MatchingCreateDto matchingCreateDto = MatchingCreateDto.builder()
                .title("test")
                .capacity(10)
                .userIds(userIds)
                .tagIds(tagIds)
                .build();

        Matching saved = matchingRepository.createMatchingAndChatroom(matchingCreateDto);

        log.info("saved: {}", saved);
        log.info("saved.getTags(): {}", saved.getMatchingTag().stream().map(matchingTag -> matchingTag.getTag().getId()).toList());
        log.info("saved.getUsers(): {}", saved.getMatchingUser().stream().map(matchingUser -> matchingUser.getUser().getId()).toList());
        log.info("saved.getChatroom(): {}", saved.getMatchingChatroom().getId());
        log.info("saved.getChatroom().getTitle(): {}", saved.getMatchingChatroom().getTitle());
        log.info("chatroom members: {}", saved.getMatchingChatroom().getChatroomMemberships().stream().map(chatroomMembership -> chatroomMembership.getUser().getId()).toList());
        assertEquals(saved.getTitle(), matchingCreateDto.getTitle());
        assertEquals(saved.getMatchingChatroom().getTitle(), matchingCreateDto.getTitle());
        assertEquals(saved.getMatchingTag().stream().map(matchingTag -> matchingTag.getTag().getId()).toList(), tagIds);
        assertEquals(saved.getMatchingUser().stream().map(matchingUser -> matchingUser.getUser().getId()).toList(), userIds);
        assertEquals(saved.getMatchingChatroom().getChatroomMemberships().stream().map(chatroomMembership -> chatroomMembership.getUser().getId()).toList(), userIds);

    }
}
