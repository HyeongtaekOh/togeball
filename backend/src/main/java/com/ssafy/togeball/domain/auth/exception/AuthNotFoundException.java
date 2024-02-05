package com.ssafy.togeball.domain.auth.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class AuthNotFoundException extends ApiException {
    public AuthNotFoundException() {
        super(AuthErrorCode.AUTH_NOT_FOUND);
    }
}
