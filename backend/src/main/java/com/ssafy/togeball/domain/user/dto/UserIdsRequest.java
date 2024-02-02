package com.ssafy.togeball.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class UserIdsRequest {
    private Set<Integer> userIds;
}