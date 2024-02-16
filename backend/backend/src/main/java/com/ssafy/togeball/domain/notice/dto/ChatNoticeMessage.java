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
    private String nickname;
    private String content;
    private MessageType type;
    private Instant timestamp;

    public static ChatNoticeMessage of(ChatMessage chatMessage) {
        return ChatNoticeMessage.builder()
                .roomId(chatMessage.getRoomId())
                .nickname(chatMessage.getNickname())
                .content(chatMessage.getContent())
                .type(chatMessage.getType())
                .timestamp(chatMessage.getTimestamp())
                .build();}
}
