package com.ssafy.togeball.domain.user.dto;


import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;

import java.time.LocalDateTime;

public class UserRequest {
    Integer id;
    String email;
    String password;
    String nickname;
    Gender gender;
    LocalDateTime birthdate;
    String phone;
    String profileImage;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .phone(phone)
                .profileImage(profileImage)
                .build();
    }

}
