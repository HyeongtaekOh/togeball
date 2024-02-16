package com.ssafy.togeball.domain.user.controller;

import com.ssafy.togeball.domain.auth.aop.UserContext;
import com.ssafy.togeball.domain.auth.exception.AuthErrorCode;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.chatroom.service.ChatroomService;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.common.s3.PreSignedURLResponse;
import com.ssafy.togeball.domain.common.s3.S3Service;
import com.ssafy.togeball.domain.post.service.PostService;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.dto.UserMeResponse;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.user.dto.UserUpdateRequest;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final S3Service s3Service;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;
    private final PostService postService;
    private final ChatroomService chatroomService;

    @GetMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam(name = "email") String email) {
        userService.checkEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam(name = "nickname") String nickname) {
        userService.checkNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest, HttpServletResponse response) {
        Integer userId = userService.signUp(userSignUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();
        authService.setTokensForUser(userId, response);

        User user = userService.findUserById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = UserResponse.of(user);

        return ResponseEntity.created(location).body(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable(name = "userId") Integer userId) {
        User user = userService.findUserById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = UserResponse.of(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/userIds")
    public ResponseEntity<?> findUsersByIdIn(@RequestParam(name = "userId") List<Integer> userIds) {
        return ResponseEntity.ok(userService.findAllByIdIn(userIds));
    }

    @UserContext
    @GetMapping("/me")
    public ResponseEntity<?> findMe(Integer userId) {
        User user = userService.findUserWithTagsAndClubById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        UserMeResponse userResponse = UserMeResponse.of(user);
        return ResponseEntity.ok(userResponse);
    }

    @UserContext
    @PatchMapping("/me")
    public ResponseEntity<?> updateMe(Integer userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @UserContext
    @GetMapping("/me/chatrooms")
    public ResponseEntity<?> findChatroomsByUserId(Integer userId,
                                                   HttpServletRequest request,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("findChatroomsByUserId userId: {}", userId);
        String token = jwtService.extractAccessToken(request).orElseThrow(() -> new ApiException(AuthErrorCode.INVALID_TOKEN));
        return ResponseEntity.ok(chatroomService.findChatroomsByUserId(userId, token, PageRequest.of(page, size)));
    }

    @UserContext
    @GetMapping("/me/chatrooms/owned")
    public ResponseEntity<?> findOwnedChatroomsByUserId(Integer userId,
                                                        @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(chatroomService.findAllRecruitChatroomsByManagerId(userId, PageRequest.of(page, size)));
    }

    @UserContext
    @GetMapping("/me/posts")
    public ResponseEntity<?> findPostsByUserId(Integer userId,
                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return ResponseEntity.ok(postService.findByUserId(userId, PageRequest.of(page, size)));
    }

    @UserContext
    @GetMapping("/me/profile/presigned-url")
    public ResponseEntity<?> getPreSignedUrlToUploadProfileImage(Integer userId) {
        String objectKey = "profiles/" + userId + "/profile";
        URL presignedUrl = s3Service.generatePresignedUrl(objectKey);

        PreSignedURLResponse response = PreSignedURLResponse.builder()
                .objectKey(s3Service.getObjectUrl() + objectKey)
                .preSignedURL(presignedUrl.toString())
                .build();

        return ResponseEntity.ok(response);
    }

    @UserContext
    @PutMapping("/me/role")
    public ResponseEntity<?> upgradeRole(Integer userId) {
        userService.upgradeRole(userId);
        return ResponseEntity.noContent().build();
    }

    @UserContext
    @PostMapping("/me/chatrooms/{chatroomId}")
    public ResponseEntity<?> joinChatroom(Integer userId, @PathVariable(value = "chatroomId") Integer chatroomId
    ) {

        chatroomService.joinChatroom(userId, chatroomId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @UserContext
    @DeleteMapping("/me/chatrooms/{chatroomId}")
    public ResponseEntity<?> leaveChatroom(Integer userId, @PathVariable(value = "chatroomId") Integer chatroomId
    ) {
        chatroomService.leaveChatroom(userId, chatroomId);
        return ResponseEntity.noContent().build();
    }
}