package com.ssafy.togeball.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String nickname = null;
}
