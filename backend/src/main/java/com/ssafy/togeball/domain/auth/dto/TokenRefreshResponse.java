package com.ssafy.togeball.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenRefreshResponse {

    private String accessToken;
    private String refreshToken;
}
