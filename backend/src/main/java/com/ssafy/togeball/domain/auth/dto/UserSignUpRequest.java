package com.ssafy.togeball.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String nickname;
}
