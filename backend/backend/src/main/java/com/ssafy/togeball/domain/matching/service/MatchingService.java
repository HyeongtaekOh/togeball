package com.ssafy.togeball.domain.matching.service;

import com.ssafy.togeball.domain.matching.dto.MatchingRequest;
import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.exception.MatchingNotFoundException;
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

    @Transactional(readOnly = true)
    public MatchingResponse findMatchingById(Integer matchingId) {
        Matching matching = matchingRepository
                .findById(matchingId)
                .orElseThrow(MatchingNotFoundException::new);
        return MatchingResponse.of(matching);
    }

    /**
     * 매칭과 매칭 채팅방을 생성하고, 매칭에 참여하는 유저들을 추가한다.
     */
    @Transactional
    public Matching createMatchingAndChatroom(MatchingRequest matchingDto) {
        log.info("createMatchingAndChatroom: {}", matchingDto);
        return matchingRepository.createMatchingAndChatroom(matchingDto);
    }
}
