package com.ssafy.togeball.domain.chatroom.dto;

import java.util.List;

public class RecruitChatroomResponse extends ChatroomResponse {

    private Integer managerId;
    private Integer gameId;
    private Integer cheeringClubId;
    private String description;
    private Integer capacity;
    private List<Integer> tagIds;
}
