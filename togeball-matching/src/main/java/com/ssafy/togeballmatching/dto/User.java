package com.ssafy.togeballmatching.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class User {
    private Integer id;
    private List<Tag> tags;
}