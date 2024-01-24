package com.ssafy.togeball.domain.matching.service;

import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.matching.dto.MatchingPostDto;
import com.ssafy.togeball.domain.matching.dto.MatchingResponseDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private final ChatroomService chatroomService;

    public Matching saveMatching(MatchingPostDto matchingDto) {

        Matching matching = matchingDto.toEntity();

        for (Integer tagId : matchingDto.getTagIds()) {
            Tag tagProxy = tagRepository.createTagProxy(tagId);
            matching.addTag(tagProxy);
        }

        for (Integer userId : matchingDto.getUserIds()) {
            User userProxy = userRepository.createUserProxy(userId);
            matching.addUser(userProxy);
        }

        Matching savedMatching = matchingRepository.save(matching);
        return savedMatching;
    }
}
