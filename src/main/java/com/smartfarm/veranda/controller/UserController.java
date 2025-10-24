package com.smartfarm.veranda.controller;

import com.smartfarm.veranda.dto.request.ChangePasswordRequest;
import com.smartfarm.veranda.dto.request.LoginRequest;
import com.smartfarm.veranda.dto.request.SignupRequest;
import com.smartfarm.veranda.dto.request.UpdateUserRequest;
import com.smartfarm.veranda.dto.response.LoginResponse;
import com.smartfarm.veranda.dto.response.SignupResponse;
import com.smartfarm.veranda.dto.response.UserResponse;
import com.smartfarm.veranda.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 컨트롤러
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인 컨트롤러
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 비밀번호 변경
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest request) {
        UserResponse response = userService.changePassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}