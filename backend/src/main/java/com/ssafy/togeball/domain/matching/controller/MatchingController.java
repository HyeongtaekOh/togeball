package com.ssafy.togeball.domain.matching.controller;

import com.ssafy.togeball.domain.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/test")
    public Map<String, Object> test() {
        return Map.of("test", "test");
    }
}
