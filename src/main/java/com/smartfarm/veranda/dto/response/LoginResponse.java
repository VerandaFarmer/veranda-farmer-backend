package com.smartfarm.veranda.dto.response;

import lombok.*;

// 로그인
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long userId;
    private String email;
    private String nickname;
    private String message;
}
