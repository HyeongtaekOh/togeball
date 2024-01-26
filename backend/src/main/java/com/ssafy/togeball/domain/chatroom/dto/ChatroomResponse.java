package com.ssafy.togeball.domain.chatroom.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ChatroomResponse {

    private Integer id;
    private String type;
    private String title;
    private LocalDateTime regdate;

    List<Integer> userIds;
}
