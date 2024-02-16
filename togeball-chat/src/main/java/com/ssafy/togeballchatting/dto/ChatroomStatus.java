package com.ssafy.togeballchatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatroomStatus {

    private Integer roomId;
    private Integer unreadCount;
    private ChatMessageDto latestChatMessage;
}
