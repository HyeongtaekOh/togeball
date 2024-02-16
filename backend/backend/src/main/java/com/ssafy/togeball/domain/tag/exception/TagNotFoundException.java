package com.ssafy.togeball.domain.tag.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class TagNotFoundException extends ApiException {

    public TagNotFoundException() {
        super(TagErrorCode.TAG_NOT_FOUND);
    }
}
