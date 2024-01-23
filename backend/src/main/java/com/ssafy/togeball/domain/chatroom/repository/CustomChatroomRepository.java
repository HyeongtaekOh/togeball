package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;

import java.util.List;

public interface CustomChatroomRepository {
    List<RecruitChatroom> findByTagIds(List<Long> tagIds);
}
