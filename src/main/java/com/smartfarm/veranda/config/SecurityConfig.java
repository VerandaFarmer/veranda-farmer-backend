package com.smartfarm.veranda.config;

import com.smartfarm.veranda.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Spring 설정 클래스
@EnableWebSecurity // Spring Security 클래스
public class SecurityConfig {

    // JwtAuthenticationFilter 주입받기
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 비밀번호 암호화 (BCrypt 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 설정 핵심!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (REST API용) -> CSRF:위조 요청 방지
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )  // 세션 사용 안 함 (JWT 사용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll()  // 회원가입, 로그인은 누구나
                        .anyRequest().authenticated()  // 나머지는 인증 필요
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

//.addFilterBefore(
//        jwtAuthenticationFilter,                      // 우리가 만든 JWT 필터
//        UsernamePasswordAuthenticationFilter.class    // 이 필터보다 먼저 실행!
//)
//```
//
//        **의미:**
//        - Spring Security의 기본 인증 필터보다 **먼저** JWT 필터를 실행
//        - 요청이 들어오면 JWT 필터가 먼저 토큰 검증!
//
//        **실행 순서:**
//        ```
//          요청
//          ↓
//         [JwtAuthenticationFilter] ← 우리가 만든 필터 (먼저!)
//              ↓
//         [UsernamePasswordAuthenticationFilter] ← Spring 기본 필터
//              ↓
//          Controller
//          ```
//
//          ---
//
//          ## 🎯 이제 완성!
//
//          **Phase 5의 핵심 구조:**
//          ```
//          1. JwtUtil          → 토큰 생성/검증 도구
//          2. JwtAuthenticationFilter → 모든 요청 검증
//          3. SecurityConfig   → Security 설정 + 필터 등록