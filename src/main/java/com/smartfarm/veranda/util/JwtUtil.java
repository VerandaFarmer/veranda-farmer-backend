package com.smartfarm.veranda.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Secret Key 생성 메서드
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // secret 문자열을 바이트 배열로 변환 한걸 hmacShakeyFo로 암호화 키로 변환
    }

    // TODO 추가

    // 1. 토큰 생성
//    입력: "hong123" (username)
//            ↓
//    JWT 토큰 생성:
//            - subject: "hong123"
//            - 발급 시간: 지금
//  - 만료 시간: 지금 + 24시간
//  - 서명: secret key로 암호화
//  ↓
//    출력: "eyJhbGc..." (JWT 토큰 문자열)
    public String generateToken(Long userId) {
        Date now = new Date(); // 현재
        Date expiryDate = new Date(now.getTime() + expiration); // 만료

        return Jwts.builder()
                .subject(userId.toString()) // 토큰 주인(username)
                .issuedAt(now) // 발급 시간
                .expiration(expiryDate) // 만료 시간
                .signWith(getSigningKey()) // 서명(암호화)
                .compact(); // 문자열로 변환
    }

    // 2. 토큰에서 userId 추출
//    입력: "eyJhbGc..." (JWT 토큰)
//            ↓
//    토큰 파싱 (해독)
//            ↓
//    subject 추출: "1"
//            ↓
//    String → Long 변환
//            ↓
//    출력: 1 (userId)
    public Long getUserIdFromToken(String token) {
        String userIdStr = Jwts.parser()
                .verifyWith(getSigningKey()) //서명검증 -> 위조 토큰 걸러냄
                .build()  // 파서 생성
                .parseSignedClaims(token) // 토큰 파싱
                .getPayload() //payLoad 가져오기
                .getSubject(); // subject 추출("1")
        return Long.parseLong(userIdStr); // "1" -> 1
    }

    // 3. 토큰 유효성 검사
//    ### **이 메서드가 하는 일:**
//            ```
//    입력: "eyJhbGc..." (JWT 토큰)
//            ↓
//    검증:
//            - 서명이 올바른가? (위조 아닌가?)
//            - 만료 안 됐나?
//            - 형식이 올바른가?
//            ↓
//    출력: true/false
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token); // 여기 까지 토큰 파싱 시도 (서명 검증, 만료시간 확인, 형식 확인)
            return true; // 검증 성공
        } catch (Exception e) {
            return false; // 검증 실패
        }
    }
}
