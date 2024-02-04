package com.ssafy.togeball.domain.user.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
