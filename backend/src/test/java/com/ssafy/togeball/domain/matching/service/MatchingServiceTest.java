package com.ssafy.togeball.domain.matching.service;

import com.ssafy.togeball.domain.matching.dto.MatchingPostDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MatchingServiceTest {

    @Autowired
    MatchingService matchingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    public void createMatchingTest() {

        User user1 = User.builder()
                .email("test@TEST")
                .nickname("nickname")
                .password("password")
                .build();
        User user2 = User.builder()
                .email("test@TES2T")
                .nickname("nickname2")
                .password("password")
                .build();
        User user3 = User.builder()
                .email("test@TES3T")
                .nickname("nickname3")
                .password("password")
                .build();


        Tag tag1 = Tag.builder()
                .type(TagType.CHEERING_STYLE)
                .content("응원가형")
                .build();
        Tag tag2 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("SSG")
                .build();
        Tag tag3 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("기아")
                .build();
        Tag tag4 = Tag.builder()
                .type(TagType.PREFERRED_TEAM)
                .content("삼성")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);

        List<Integer> userIds = List.of(user1.getId(), user2.getId(), user3.getId());
        List<Integer> tagIds = List.of(tag1.getId(), tag2.getId(), tag3.getId(), tag4.getId());

        MatchingPostDto matchingPostDto = new MatchingPostDto();
        matchingPostDto.setTitle("test");
        matchingPostDto.setCapacity(10);
        matchingPostDto.setUserIds(userIds);
        matchingPostDto.setTagIds(tagIds);

        Matching matching = matchingPostDto.toEntity();
        Matching saved = matchingService.saveMatching(matchingPostDto);

        log.info("saved: {}", saved);
        log.info("saved.getTags(): {}", saved.getMatchingTag().stream().map(matchingTag -> matchingTag.getTag().getId()).toList());
        log.info("saved.getUsers(): {}", saved.getMatchingUser().stream().map(matchingUser -> matchingUser.getUser().getId()).toList());
    }
}
