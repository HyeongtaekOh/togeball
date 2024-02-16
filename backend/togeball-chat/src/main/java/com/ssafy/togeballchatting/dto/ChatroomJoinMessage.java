package com.ssafy.togeballchatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatroomJoinMessage {

    private Type type;
    private Integer roomId;
    private Integer userId;
    private String nickname;

    public static enum Type {
        JOIN, LEAVE
    }
}
