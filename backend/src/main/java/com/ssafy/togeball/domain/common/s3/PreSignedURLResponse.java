package com.ssafy.togeball.domain.common.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PreSignedURLResponse {
    private String preSignedURL;
    private String objectKey;
}
