package com.ssafy.togeball.domain.post.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class PostNotFoundException extends ApiException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
