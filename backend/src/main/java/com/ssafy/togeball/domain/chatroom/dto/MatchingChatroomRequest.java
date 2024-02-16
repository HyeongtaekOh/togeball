package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MatchingChatroomRequest {

    private String title;
    private Integer capacity;
    private List<Integer> userIds;
}
