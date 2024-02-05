package com.ssafy.togeball.domain.auth.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_TOKEN);
    }
}