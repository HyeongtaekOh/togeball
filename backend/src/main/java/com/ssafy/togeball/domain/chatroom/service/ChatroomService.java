package com.ssafy.togeball.domain.chatroom.service;

import com.ssafy.togeball.domain.chatroom.dto.ChatroomResponse;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomRequest;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.ChatroomMembership;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomMembershipRepository;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final ChatroomMembershipRepository chatroomMembershipRepository;

    @Transactional
    public ChatroomResponse findChatroomById(Integer chatroomId) {
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroomId);
        return null;
    }

    @Transactional
    public RecruitChatroom createRecruitChatroom(RecruitChatroomRequest chatroomDto) {
        return chatroomRepository.createRecruitChatroom(chatroomDto);
    }

    @Transactional
    public RecruitChatroom updateRecruitChatroom(RecruitChatroomRequest chatroomDto) {
        return chatroomRepository.updateRecruitChatroom(chatroomDto);
    }

    @Transactional
    public void deleteChatroom(Integer chatroomId) {
        chatroomRepository.deleteById(chatroomId);
    }

    @Transactional
    public void joinChatroom(Integer userId, Integer chatroomId) {
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
