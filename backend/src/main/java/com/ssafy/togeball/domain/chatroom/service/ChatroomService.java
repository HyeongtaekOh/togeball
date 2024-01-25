package com.ssafy.togeball.domain.chatroom.service;

import com.ssafy.togeball.domain.chatroom.dto.ChatroomCreateDto;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.ChatroomMembership;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomMembershipRepository;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMembershipRepository chatroomMembershipRepository;

    @Transactional
    public MatchingChatroom createMatchingChatroom(ChatroomCreateDto chatroomDto, Matching matching) {
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title(chatroomDto.getTitle())
                .matching(matching)
                .build();
        return chatroomRepository.save(matchingChatroom);
    }

    @Transactional
    public void joinChatroom(Integer userId, Integer chatroomId) {
//        User user = userRepository.findById(userId).orElseThrow();
//        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow();
//
//        ChatroomMembership membership = ChatroomMembership.builder()
//                .user(user)
//                .chatroom(chatroom)
//                .build();
//
//        chatroomMembershipRepository.save(membership);
        chatroomRepository.addUser(chatroomId, userId);
    }

    @Transactional
    public void leaveChatroom(Integer userId, Integer chatroomId) {
        ChatroomMembership membership = chatroomMembershipRepository
                .findByUserIdAndChatroomId(userId, chatroomId).orElseThrow();
        chatroomMembershipRepository.delete(membership);
    }

    public Page<Chatroom> findAllChatroomsByTitleContaining(String title, Pageable pageable) {
        return chatroomRepository.findAllByTitleContaining(title, pageable);
    }

    public Page<Chatroom> findAllChatroomsByType(String type, Pageable pageable) {
        return chatroomRepository.findAllByType(type, pageable);
    }

    public Page<Chatroom> findAllChatroomsByUserId(Integer userId, Pageable pageable) {
        return chatroomRepository.findAllChatroomsByUserId(userId, pageable);
    }

    public Page<RecruitChatroom> findAllRecruitChatroomsByManagerId(Integer managerId, Pageable pageable) {
        return chatroomRepository.findAllRecruitChatroomsByManagerId(managerId, pageable);
    }
}
