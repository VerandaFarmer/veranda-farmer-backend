package com.smartfarm.veranda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 회원가입
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {
    private Long userId;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private String message;
}
