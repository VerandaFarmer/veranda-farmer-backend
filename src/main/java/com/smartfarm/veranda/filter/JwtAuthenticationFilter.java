package com.smartfarm.veranda.filter;

import com.smartfarm.veranda.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

// 요청 당 1번만 실행되는 필터
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // jwt 주입
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 핵심 로직 -> 모든 요청마다 실행
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 헤더에서 토큰 추출
        String token = getTokenFromRequest(request);

        // 2. 토큰이 있고 유효하면
        if (token != null && jwtUtil.validateToken(token)) {
            // 3. 토큰에서 userId 추출
            Long userId = jwtUtil.getUserIdFromToken(token);

            // 4. Spring Security에 인증 정보 저장
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,      // principal (인증된 사용자)
                            null,        // credentials (비밀번호는 필요 없음)
                            new ArrayList<>()  // authorities (권한 목록)
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. 다음 필터로 넘기기
        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 JWT 토큰 추출 -> 헬퍼 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // "Bearer eyJhbGc..." 형식에서 "eyJhbGc..." 부분만 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 제거
        }

        return null;
    }
}

//        **예시:**
//        ```
//        입력: "Bearer eyJhbGc..."
//        출력: "eyJhbGc..."
//        ```
//
//        ---
//
//        ## 🎯 필터 완성!
//
//        **JwtAuthenticationFilter가 하는 일 요약:**
//        ```
//        1. 요청 들어옴
//              ↓
//        2. 헤더에서 토큰 추출
//              ↓
//        3. 토큰 검증 (유효한가?)
//              ↓
//        4. userId 추출
//              ↓
//        5. Spring Security에 "인증됨!" 표시
//              ↓
//        6. Controller로 넘김