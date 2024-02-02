package com.ssafy.togeball.domain.user.controller;

import com.ssafy.togeball.domain.user.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.tag.dto.TagIdsRequest;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception {
        try {
            Integer userId = userService.signUp(userSignUpRequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{userId}")
                    .buildAndExpand(userId)
                    .toUri();
            return ResponseEntity.created(location).body(Map.of("id", userId));
        } catch (RuntimeException e) {
            // TODO : Exception 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable(name = "userId") Integer userId) {
        try {
            return ResponseEntity.ok(userService.findUserById(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/tags")
    public ResponseEntity<?> updateUserTags(@PathVariable(name = "userId") Integer userId, @RequestBody TagIdsRequest tagIdsRequest) {
        try {
            userService.updateUserTags(userId, tagIdsRequest.getTagIds());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
