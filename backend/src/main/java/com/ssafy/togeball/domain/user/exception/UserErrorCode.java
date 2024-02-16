package com.ssafy.togeball.domain.user.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    CONFLICT_EMAIL(HttpStatus.CONFLICT, "Email is already registered"),
    CONFLICT_NICKNAME(HttpStatus.CONFLICT, "Nickname is already registered"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not found"),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "Invalid user Id"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
