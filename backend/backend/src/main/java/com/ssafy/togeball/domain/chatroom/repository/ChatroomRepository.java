package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer>, CustomChatroomRepository {

    Page<Chatroom> findAllByTitleContaining(String title, Pageable pageable);
    Page<Chatroom> findAllByType(String type, Pageable pageable);

    @Query("SELECT c.capacity FROM Chatroom c WHERE c.id = :id")
    Integer findCapacityById(Integer id);

    @Query("SELECT gc FROM GameChatroom gc WHERE gc.game.id = :gameId")
    Optional<GameChatroom> findGameChatroomByGameId(Integer gameId);
}
