package com.ssafy.togeball.domain.chatroom.dto;

import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ChatroomResponse {

    private Integer id;
    private String type;
    private String title;
    private Integer capacity;

    @Setter
    List<UserResponse> members;

    @Setter
    private ChatroomStatus status;
}
