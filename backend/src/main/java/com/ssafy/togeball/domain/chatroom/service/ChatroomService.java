package com.ssafy.togeball.domain.chatroom.service;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.ChatroomMembership;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomMembershipRepository;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMembershipRepository chatroomMembershipRepository;

    @Transactional
    public void joinChatroom(Integer userId, Integer chatroomId) {
        User user = userRepository.findById(userId).orElseThrow();
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow();

        ChatroomMembership membership = ChatroomMembership.builder()
                .user(user)
                .chatroom(chatroom)
                .build();

        chatroomMembershipRepository.save(membership);
    }

    @Transactional
    public void leaveChatroom(Integer userId, Integer chatroomId) {
        ChatroomMembership membership = chatroomMembershipRepository
                .findByUserIdAndChatroomId(userId, chatroomId).orElseThrow();
        chatroomMembershipRepository.delete(membership);
    }
}
