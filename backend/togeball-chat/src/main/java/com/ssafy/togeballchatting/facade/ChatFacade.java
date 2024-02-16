package com.ssafy.togeballchatting.facade;

import com.ssafy.togeballchatting.dto.*;
import com.ssafy.togeballchatting.entity.ChatMessage;
import com.ssafy.togeballchatting.entity.MessageType;
import com.ssafy.togeballchatting.exception.NotFoundMessageException;
import com.ssafy.togeballchatting.exception.NotParticipatingException;
import com.ssafy.togeballchatting.service.ChatHistoryService;
import com.ssafy.togeballchatting.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatFacade {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.notification.routing-key}")
    private String notificationRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ChatHistoryService chatHistoryService;
    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendChatMessage(Integer roomId, ChatMessageDto messageDto) {
        ChatMessageDto message = chatMessageService.save(messageDto);
        log.info("message: {}", message);
        messagingTemplate.convertAndSend("/topic/room." + roomId, message);
        if (message.getType() != MessageType.NOTICE) {
            rabbitTemplate.convertAndSend(exchange, notificationRoutingKey, message);
            ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, message.getSenderId());
            if (chatHistoryDto == null) {
                throw new NotParticipatingException("사용자가 채팅방에 참여하지 않았습니다.");
            }
            if (message.getTimestamp().isAfter(chatHistoryDto.getLastReadTimestamp())) {
                chatHistoryDto.setLastReadTimestamp(message.getTimestamp());
                chatHistoryService.save(chatHistoryDto);
            }
        }
    }

    @Transactional(readOnly = true)
    public Page<ChatMessageDto> findGameChatMessagePageByRoomId(Integer roomId, Pageable pageable) {
        return chatMessageService.findAllGameChatMessageByRoomId(roomId, pageable);
    }

    @Transactional
    public Page<ChatMessageDto> findChatMessagePageByRoomId(Integer roomId, Integer userId, Pageable pageable) {
        log.info("roomId: {}, userId: {}, pageable: {}", roomId, userId, pageable);

        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            throw new NotParticipatingException("사용자가 채팅방에 참여하지 않았습니다.");
        }

        Page<ChatMessageDto> page = chatMessageService.findAllByRoomIdAndEnteredTime(roomId, chatHistoryDto.getEnteredTimestamp(), pageable);
        log.info("page.getContent(): {}", page.getContent());
        if (page.getContent().isEmpty()) {
            return page;
        }

        // 사용자가 채팅방에서 읽은 메시지 중 가장 최근 메시지의 타임스탬프를 업데이트
        Instant lastReadTimestamp = page.getContent().get(page.getContent().size() - 1).getTimestamp();
        if (lastReadTimestamp.isAfter(chatHistoryDto.getLastReadTimestamp())) {
            chatHistoryDto.setLastReadTimestamp(lastReadTimestamp);
            chatHistoryService.save(chatHistoryDto);
        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<ChatroomStatus> getUnreadMessageCountAndLatestChatMessage(Integer userId, List<Integer> roomIds) {
        List<ChatroomStatus> response = new ArrayList<>();
        for (Integer roomId : roomIds) {
            Integer unreadCount = countUnreadMessages(roomId, userId);
            ChatMessageDto latestChatMessage = getLatestChatMessage(roomId, userId);
            response.add(ChatroomStatus.builder()
                    .roomId(roomId)
                    .unreadCount(unreadCount)
                    .latestChatMessage(latestChatMessage)
                    .build());
        }
        log.info("response: {}", response);
        return response;
    }

    @Transactional(readOnly = true)
    public Integer countUnreadMessages(Integer roomId, Integer userId) {
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            return 0;
        }
        return chatMessageService.countUnreadMessages(roomId, chatHistoryDto.getLastReadTimestamp());
    }

    @Transactional(readOnly = true)
    public ChatMessageDto getLatestChatMessage(Integer roomId, Integer userId) {
        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            return null;
        }
        return chatMessageService.getLatestChatMessage(roomId, chatHistoryDto.getEnteredTimestamp());
    }

    @Transactional
    @RabbitListener(queues = "${rabbitmq.join.queue}")
    public void handleJoinEvent(ChatroomJoinMessage message) {
        log.info("message: {}", message);
        if (message.getType() == ChatroomJoinMessage.Type.LEAVE) {
            leaveChatroom(message.getUserId(), message.getRoomId(), message.getNickname());
        }
        else if (chatHistoryService.findChatHistoryByRoomIdAndUserId(message.getRoomId(), message.getUserId()) == null) {
            joinChatroom(message.getUserId(), message.getRoomId(), message.getNickname());
        }
    }

    private void joinChatroom(Integer userId, Integer roomId, String nickname) {
        Instant now = Instant.now();
        log.info("now: {}", now);
        ChatHistoryDto save = chatHistoryService.save(ChatHistoryDto.builder()
                .roomId(roomId)
                .userId(userId)
                .enteredTimestamp(now)
                .lastReadTimestamp(now)
                .build());
        log.info("save: {}", save);
        sendChatMessage(roomId, ChatMessageDto.builder()
                .type(MessageType.NOTICE)
                .roomId(roomId)
                .content(nickname + " 님이 입장하셨습니다.")
                .build());
    }

    private void leaveChatroom(Integer userId, Integer roomId, String nickname) {
        chatHistoryService.deleteByRoomIdAndUserId(roomId, userId);
        sendChatMessage(roomId, ChatMessageDto.builder()
                .type(MessageType.NOTICE)
                .roomId(roomId)
                .content(nickname + " 님이 나가셨습니다.")
                .build());
    }

    @Transactional
    public void updateReadStatus(Integer userId, Integer roomId, ChatReadDto chatReadDto) {

        ChatMessageDto lastReadMessage = chatMessageService.findById(chatReadDto.getLastReadMessageId());
        if (lastReadMessage == null || !lastReadMessage.getRoomId().equals(roomId)){
            throw new NotFoundMessageException("잘못된 메시지 ID입니다.");
        }

        ChatHistoryDto chatHistoryDto = chatHistoryService.findChatHistoryByRoomIdAndUserId(roomId, userId);
        if (chatHistoryDto == null) {
            throw new NotParticipatingException("사용자가 채팅방에 참여하지 않았습니다.");
        }

        chatHistoryDto.setLastReadTimestamp(lastReadMessage.getTimestamp());
        chatHistoryService.save(chatHistoryDto);
    }
}
