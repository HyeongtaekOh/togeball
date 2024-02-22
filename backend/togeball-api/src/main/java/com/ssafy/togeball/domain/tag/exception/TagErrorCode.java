package com.ssafy.togeball.domain.tag.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TagErrorCode implements ErrorCode {

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "Tag is not found"),
    DUPLICATE_TAG(HttpStatus.CONFLICT, "Tag already exists"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

