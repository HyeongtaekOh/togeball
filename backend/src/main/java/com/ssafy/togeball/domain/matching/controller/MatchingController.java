package com.ssafy.togeball.domain.matching.controller;

import com.ssafy.togeball.domain.matching.dto.MatchingRequest;
import com.ssafy.togeball.domain.matching.dto.MatchingResponse;
import com.ssafy.togeball.domain.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/{matchingId}")
    public ResponseEntity<?> getMatching(@PathVariable Integer matchingId) {
        MatchingResponse matching = matchingService.findMatchingById(matchingId);
        return ResponseEntity.ok(matching);
    }

    @PostMapping
    public ResponseEntity<?> createMatching(@RequestBody MatchingRequest matchingRequest) {
        MatchingResponse matching = matchingService.createMatchingAndChatroom(matchingRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{matchingId}")
                .buildAndExpand(matching.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
