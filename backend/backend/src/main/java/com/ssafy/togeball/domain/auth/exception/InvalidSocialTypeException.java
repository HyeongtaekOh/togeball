package com.ssafy.togeball.domain.auth.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class InvalidSocialTypeException extends ApiException {
    public InvalidSocialTypeException() {
        super(AuthErrorCode.INVALID_SOCIAL_TYPE);
    }
}
