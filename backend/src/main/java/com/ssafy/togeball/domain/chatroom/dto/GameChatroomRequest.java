package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameChatroomRequest {

    private String title;
    private Integer gameId;
}
