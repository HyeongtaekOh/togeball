package com.ssafy.togeball.domain.chatroom.service;

import com.ssafy.togeball.domain.chatroom.dto.*;
import com.ssafy.togeball.domain.chatroom.entity.*;
import com.ssafy.togeball.domain.chatroom.exception.ChatroomErrorCode;
import com.ssafy.togeball.domain.chatroom.exception.ChatroomNotFoundException;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomMembershipRepository;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.chat.routing-key}")
    private String routingKey;

    private final WebClient webClient;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;
    private final ChatroomRepository chatroomRepository;
    private final ChatroomMembershipRepository chatroomMembershipRepository;

    public Chatroom getTestChatroom() {
        return chatroomRepository.findById(1).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findParticipantsByChatroomId(Integer chatroomId) {

        chatroomRepository.findById(chatroomId).orElseThrow(ChatroomNotFoundException::new);
        List<User> participants =  chatroomRepository.findParticipantsByChatroomId(chatroomId);
        return participants.stream().map(UserResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public ChatroomResponse findChatroomById(Integer chatroomId) {
        Optional<Chatroom> optional = chatroomRepository.findById(chatroomId);
        if (optional.isEmpty()) {
            return null;
        }
        Chatroom chatroom = optional.get();
        return convertChatroomResponse(chatroom);
    }

    @Transactional(readOnly = true)
    public Page<ChatroomResponse> findChatroomsByUserId(Integer userId, String token, Pageable pageable) {
        log.info("userId: {}", userId);
        Page<Chatroom> chatrooms = chatroomRepository.findChatroomsByUserId(userId, pageable);
        log.info("chatrooms: {}", chatrooms.map(Chatroom::getId).toList());
        Page<ChatroomResponse> chatroomResponses = getChatroomResponses(chatrooms);

        if (chatroomResponses.isEmpty()) {
            return chatroomResponses;
        }

        Map<Integer, ChatroomStatus> statuses =
                getChatroomStatuses(userId, token, chatrooms.map(Chatroom::getId).toList());
        log.info("statuses: {}", statuses);
        chatroomResponses.forEach(chatroom -> addChatroomStatus(chatroom, statuses.get(chatroom.getId())));
        return chatroomResponses;
    }

    @Transactional(readOnly = true)
    public Page<RecruitChatroomResponse> findRecruitChatroomsByCondition(RecruitChatroomSearchCondition condition, Pageable pageable) {
        Page<RecruitChatroom> chatrooms = chatroomRepository.findRecruitChatroomsByCondition(condition, pageable);
        return chatrooms.map(RecruitChatroomResponse::of);
    }

    @Transactional
    public Integer createRecruitChatroom(RecruitChatroomRequest chatroomDto) {
        return chatroomRepository.createRecruitChatroom(chatroomDto).getId();
    }

    @Transactional
    public Integer createGameChatroom(GameChatroomRequest chatroomDto) {
        return chatroomRepository.createGameChatroom(chatroomDto).getId();
    }

    @Transactional
    public RecruitChatroomResponse updateRecruitChatroom(RecruitChatroomRequest chatroomDto) {
        chatroomRepository.findById(chatroomDto.getId()).orElseThrow(ChatroomNotFoundException::new);
        RecruitChatroom recruitChatroom = chatroomRepository.updateRecruitChatroom(chatroomDto);
        return RecruitChatroomResponse.of(recruitChatroom);
    }

    @Transactional
    public void deleteChatroom(Integer chatroomId) {
        chatroomRepository.findById(chatroomId).orElseThrow(ChatroomNotFoundException::new);
        chatroomRepository.deleteById(chatroomId);
    }

    @Transactional
    public void joinChatroom(Integer userId, Integer chatroomId) {

        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty()) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (chatroomRepository.findCapacityById(chatroomId)
                <= chatroomMembershipRepository.countByChatroomId(chatroomId)) {
            throw new ApiException(ChatroomErrorCode.CHATROOM_JOIN_FAILED);
        }
        log.info("join userId: {}, chatroomId: {}", userId, chatroomId);
        rabbitTemplate.convertAndSend(exchange, routingKey, ChatroomJoinMessage.builder()
                .type(ChatroomJoinMessage.Type.JOIN)
                .userId(userId)
                .roomId(chatroomId)
                .nickname(user.get().getNickname())
                .build(), new CorrelationData(String.valueOf(userId)));
        chatroomRepository.addParticipant(chatroomId, userId);
    }

    @Transactional
    public void leaveChatroom(Integer userId, Integer chatroomId) {

        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty()) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        ChatroomMembership membership = chatroomMembershipRepository
                .findByUserIdAndChatroomId(userId, chatroomId).orElseThrow(ChatroomNotFoundException::new);
        chatroomMembershipRepository.delete(membership);
        rabbitTemplate.convertAndSend(exchange, routingKey, ChatroomJoinMessage.builder()
                .type(ChatroomJoinMessage.Type.LEAVE)
                .userId(userId)
                .roomId(chatroomId)
                .nickname(user.get().getNickname())
                .build(), new CorrelationData(String.valueOf(userId)));
    }

    @Transactional(readOnly = true)
    public Page<ChatroomResponse> findAllChatroomsByType(String type, Pageable pageable) {

        Page<Chatroom> chatrooms = chatroomRepository.findAllByType(type, pageable);
        return getChatroomResponses(chatrooms);
    }

    @Transactional(readOnly = true)
    public Page<RecruitChatroomResponse> findAllRecruitChatroomsByManagerId(Integer managerId, Pageable pageable) {
        Page<RecruitChatroom> chatrooms = chatroomRepository.findRecruitChatroomsByManagerId(managerId, pageable);
        return chatrooms.map(RecruitChatroomResponse::of);
    }

    private ChatroomResponse convertChatroomResponse(Chatroom chatroom) {

        List<UserResponse> members = chatroom.getChatroomMemberships().stream()
                .map(membership -> UserResponse.of(membership.getUser()))
                .toList();

        ChatroomResponse chatroomResponse = null;

        if (chatroom instanceof RecruitChatroom) {
            chatroomResponse = RecruitChatroomResponse.of((RecruitChatroom) chatroom);
        } else if (chatroom instanceof MatchingChatroom) {
            chatroomResponse = MatchingChatroomResponse.of((MatchingChatroom) chatroom);
        } else if (chatroom instanceof GameChatroom) {
            chatroomResponse = GameChatroomResponse.of((GameChatroom) chatroom);
        } else {
            throw new ApiException(ChatroomErrorCode.INVALID_CHATROOM_TYPE);
        }

        chatroomResponse.setMembers(members);
        return chatroomResponse;
    }

    private void addChatroomStatus(ChatroomResponse chatroom, ChatroomStatus status) {
        chatroom.setStatus(status);
    }

    private Page<ChatroomResponse> getChatroomResponses(Page<Chatroom> chatrooms) {
        return chatrooms.map(this::convertChatroomResponse);
    }

    private Map<Integer, ChatroomStatus> getChatroomStatuses(Integer userId, String token, List<Integer> roomIds) {

        List<ChatroomStatus> response = webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/chat-server/me" + "/chats/unread");
                    roomIds.forEach(roomId -> uriBuilder.queryParam("roomId", roomId));
                    return uriBuilder.build();
                })
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(ChatroomStatus.class)
                .collectList()
                .block();
        Map<Integer, ChatroomStatus> statuses = new HashMap<>();
        response.forEach(status -> statuses.put(status.getRoomId(), status));
        return statuses;
    }

    @Transactional
    public GameChatroomResponse findGameChatroomByGameId(Integer gameId) {
        GameChatroom chatroom = chatroomRepository.findGameChatroomByGameId(gameId).orElseThrow(ChatroomNotFoundException::new);
        return GameChatroomResponse.of(chatroom);
    }
}
