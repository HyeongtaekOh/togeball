package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.ChatroomMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatroomMembershipRepository extends JpaRepository<ChatroomMembership, Integer> {

    Optional<ChatroomMembership> findByUserIdAndChatroomId(Integer userId, Integer chatroomId);

    Integer countByChatroomId(Integer chatroomId);
}
