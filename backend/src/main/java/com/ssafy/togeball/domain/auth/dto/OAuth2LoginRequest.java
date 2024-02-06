package com.ssafy.togeball.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2LoginRequest {
    String code;
    String provider;
}
