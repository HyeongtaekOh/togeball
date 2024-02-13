package com.ssafy.togeball.domain.notice.dto;

import com.ssafy.togeball.domain.chatroom.dto.ChatMessage;
import com.ssafy.togeball.domain.chatroom.dto.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class ChatNoticeMessage {

    private Integer roomId;
    private MessageType type;
    private String content;
    private Instant timestamp;

    public static ChatNoticeMessage of(ChatMessage chatMessage) {
        return ChatNoticeMessage.builder()
                .roomId(chatMessage.getRoomId())
                .type(chatMessage.getType())
                .content(chatMessage.getContent())
                .timestamp(chatMessage.getTimestamp())
                .build();}
}
