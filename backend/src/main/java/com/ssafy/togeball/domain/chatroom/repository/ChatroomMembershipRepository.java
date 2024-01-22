package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.ChatroomMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatroomMembershipRepository extends JpaRepository<ChatroomMembership, Long> {

    Optional<ChatroomMembership> findByUserIdAndChatroomId(Long userId, Long chatroomId);
}
