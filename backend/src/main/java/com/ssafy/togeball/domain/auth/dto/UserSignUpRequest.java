package com.ssafy.togeball.domain.auth.dto;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String nickname;
}
