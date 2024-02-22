package com.ssafy.togeball.domain.chatroom.dto;

import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatroomStatusResponse {

    private Integer id;
    private String type;
    private String title;
    private Integer capacity;
    List<UserResponse> members;
}
