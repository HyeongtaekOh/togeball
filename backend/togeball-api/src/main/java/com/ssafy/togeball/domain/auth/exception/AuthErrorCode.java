package com.ssafy.togeball.domain.auth.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token"),
    AUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "Authentication information not found"),
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "Invalid Social Type"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
