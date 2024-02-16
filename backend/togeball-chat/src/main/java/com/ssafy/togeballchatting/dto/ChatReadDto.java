package com.ssafy.togeballchatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatReadDto {

    private String lastReadMessageId;
}
