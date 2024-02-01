package com.ssafy.togeball.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserIdsRequest {
    private Set<Integer> userIds;
}