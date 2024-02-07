package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class ChatMessage {

    private Integer senderId;
    private String nickname;
    private Integer roomId;
    private MessageType type;
    private String content;
    private Instant timestamp;
}
