package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatroomJoinMessage {

    private Type type;
    private Integer roomId;
    private Integer userId;
    private String nickname;

    public static enum Type {
        JOIN, LEAVE
    }
}
