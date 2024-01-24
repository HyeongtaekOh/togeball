package com.ssafy.togeball.domain.matching.controller;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.matching.dto.MatchingPostDto;
import com.ssafy.togeball.domain.matching.dto.MatchingResponseDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;
    private final ChatroomService chatroomService;

    @GetMapping("/test")
    public Map<String, Object> test() {
        Matching matching = Matching.builder()
                .title("title")
                .capacity(10)
                .build();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .matching(matching)
                .title("title")
                .build();

        Chatroom matchingChatroomFromDB = chatroomService.saveChatroom(matchingChatroom);

        return Map.of(
                "matching", matching,
                "matchingfromDB", matchingChatroom.getMatching(),
                "matchingChatroomFromDB", matchingChatroomFromDB
        );
    }

//    @PostMapping
//    public ResponseEntity<MatchingResponseDto> createMatching(@RequestBody MatchingPostDto matchingPostDto) {
//
//        MatchingResponseDto matchingResponseDto = matchingService.saveMatching(matchingPostDto);
//
//        return ResponseEntity.ok(MatchingResponseDto.builder()
//                .matching(matching)
//                .matchingChatroom(matchingChatroomFromDB)
//                .build());
//    }
}
