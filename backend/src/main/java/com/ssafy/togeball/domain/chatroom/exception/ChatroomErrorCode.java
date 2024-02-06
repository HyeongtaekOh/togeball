package com.ssafy.togeball.domain.chatroom.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatroomErrorCode implements ErrorCode {

    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "Chatroom is not found"),
    PARTICIPANTS_NOT_FOUND(HttpStatus.NOT_FOUND, "Participants are not found for the chatroom"),
    CHATROOM_CAPACITY_EXCEEDED(HttpStatus.FORBIDDEN, "Chatroom capacity exceeded"),
    CHATROOM_JOIN_FAILED(HttpStatus.BAD_REQUEST, "Failed to join the chatroom"),
    CHATROOM_LEAVE_FAILED(HttpStatus.BAD_REQUEST, "Failed to leave the chatroom"),
    INVALID_CHATROOM_TYPE(HttpStatus.BAD_REQUEST, "Invalid chatroom type"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}