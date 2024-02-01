package com.ssafy.togeball.domain.user.dto;


import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequest {

    private Integer id;
    private String email;
    private String nickname;
    private Gender gender;
    private LocalDateTime birthdate;
    private String phone;
    private String profileImage;

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .phone(phone)
                .profileImage(profileImage)
                .build();
    }
}
