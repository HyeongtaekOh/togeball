package com.ssafy.togeball.domain.chatroom.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class ChatroomNotFoundException extends ApiException {
    public ChatroomNotFoundException() {
        super(ChatroomErrorCode.CHATROOM_NOT_FOUND);
    }
}
