package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.dto.GameChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.MatchingChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomSearchCondition;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomChatroomRepository {

    List<User> findParticipantsByChatroomId(Integer chatroomId);

    Page<Chatroom> findChatroomsByUserId(Integer userId, Pageable pageable);

    Page<RecruitChatroom> findRecruitChatroomsByCondition(RecruitChatroomSearchCondition condition, Pageable pageable);

    Page<RecruitChatroom> findRecruitChatroomsByManagerId(Integer managerId, Pageable pageable);

    boolean addParticipant(Integer chatroomId, Integer userId);

    void addParticipants(Integer chatroomId, List<Integer> userIds);

    RecruitChatroom createRecruitChatroom(RecruitChatroomRequest chatroomDto);

    GameChatroom createGameChatroom(GameChatroomRequest chatroomDto);

    MatchingChatroom createMatchingChatroom(MatchingChatroomRequest chatroomDto);

    RecruitChatroom updateRecruitChatroom(RecruitChatroomRequest recruitChatroomDto);

    void updateRecruitChatroomTags(Integer chatroomId, List<Integer> tagIds);

}
