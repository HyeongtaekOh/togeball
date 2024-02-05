package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatroomJoinMessage {

    private Integer roomId;
    private Integer userId;
}
