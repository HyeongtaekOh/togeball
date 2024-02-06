package com.ssafy.togeball.domain.auth.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class ExpiredTokenException extends ApiException {

    public ExpiredTokenException() {
        super(AuthErrorCode.EXPIRED_TOKEN);
    }
}
