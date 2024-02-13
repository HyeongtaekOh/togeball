package com.ssafy.togeball.domain.notice.facade;

import com.ssafy.togeball.domain.chatroom.dto.ChatMessage;
import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.notice.dto.ChatNoticeMessage;
import com.ssafy.togeball.domain.notice.service.SseService;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatNoticeFacade {

    private final SseService sseService;
    private final ChatroomService chatroomService;

    @Transactional
    @RabbitListener(queues = "${rabbitmq.notification.chat.queue}")
    public void sendChatNotification(ChatMessage message) {
        log.info("message: {}", message);
        List<Integer> participantIds = chatroomService.findParticipantsByChatroomId(message.getRoomId())
                .stream()
                .map(UserResponse::getId)
                .toList();
        sseService.sendToUsers(participantIds, "chat", ChatNoticeMessage.of(message));
    }
}
