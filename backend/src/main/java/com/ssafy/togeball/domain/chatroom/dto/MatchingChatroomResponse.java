package com.ssafy.togeball.domain.chatroom.dto;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MatchingChatroomResponse extends ChatroomResponse {

    private MatchingResponse matching;

    public static MatchingChatroomResponse of(MatchingChatroom chatroom) {

        MatchingChatroomResponse response = MatchingChatroomResponse.builder()
                .id(chatroom.getId())
                .type(chatroom.getType())
                .title(chatroom.getTitle())
                .matching(MatchingResponse.of(chatroom.getMatching()))
                .build();
        return response;
    }
}
