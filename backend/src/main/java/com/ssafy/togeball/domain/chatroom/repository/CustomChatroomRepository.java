package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomChatroomRepository {

    void addUser(Integer chatroomId, Integer userId);

    void addAllUsers(Integer chatroomId, List<Integer> userIds);

    Page<RecruitChatroom> findByTagIds(List<Integer> tagIds, Pageable pageable);

    Page<Chatroom> findAllChatroomsByUserId(Integer userId, Pageable pageable);

    Page<RecruitChatroom> findAllRecruitChatroomsByManagerId(Integer managerId, Pageable pageable);
}
