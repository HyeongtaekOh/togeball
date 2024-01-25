package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ChatroomCreateDto {

    private String title;
    private List<Integer> userIds;
}
