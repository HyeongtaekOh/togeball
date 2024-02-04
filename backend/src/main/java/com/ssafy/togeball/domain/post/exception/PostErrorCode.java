package com.ssafy.togeball.domain.post.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post is not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
