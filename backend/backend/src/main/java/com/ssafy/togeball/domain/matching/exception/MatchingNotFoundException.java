package com.ssafy.togeball.domain.matching.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;

public class MatchingNotFoundException extends ApiException {
    public MatchingNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
