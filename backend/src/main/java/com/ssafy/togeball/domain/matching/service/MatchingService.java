package com.ssafy.togeball.domain.matching.service;

import com.ssafy.togeball.domain.matching.dto.MatchingRequest;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;

    /**
     * 매칭과 매칭 채팅방을 생성하고, 매칭에 참여하는 유저들을 추가한다.
     */
    @Transactional
    public Matching createMatchingAndChatroom(MatchingRequest matchingDto) {
        return matchingRepository.createMatchingAndChatroom(matchingDto);
    }
}
