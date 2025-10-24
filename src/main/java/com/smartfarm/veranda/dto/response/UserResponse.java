package com.smartfarm.veranda.dto.response;

import lombok.*;

import java.time.LocalDateTime;

// 회원 정보 조회
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
