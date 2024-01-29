package com.ssafy.togeball.domain.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UserSignInRequest {

    private String email;
    private String password;
    private String nickname;
}