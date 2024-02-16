package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {

    private Integer id;
    private String nickname;
    private String gender;
    private LocalDateTime birthdate;
    private String profileImage;
    private List<TagResponse> tags;
    private String clubSponsorName;
    private String clubName;
    private String clubLogo;
}
