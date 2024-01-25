package com.ssafy.togeball.domain.matching.service;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.matching.dto.MatchingCreateDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Matching createMatching(MatchingCreateDto matchingDto) {

        Matching matching = matchingDto.toEntity();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .matching(matching)
                .title(matching.getTitle())
                .build();
        matching.setMatchingChatroom(matchingChatroom);

        for (Integer tagId : matchingDto.getTagIds()) {
            Tag tagProxy = tagRepository.createTagProxy(tagId);
            matching.addTag(tagProxy);
        }
        for (Integer userId : matchingDto.getUserIds()) {
            User userProxy = userRepository.createUserProxy(userId);
            matching.addUser(userProxy);
            matchingChatroom.addMember(userProxy);
        }

        return matchingRepository.save(matching);
    }
}
