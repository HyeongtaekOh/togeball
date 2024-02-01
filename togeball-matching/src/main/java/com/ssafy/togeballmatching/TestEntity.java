package com.ssafy.togeballmatching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    private String name;
    private Integer age;
    private Double height;
    private Instant createdAt;
}
