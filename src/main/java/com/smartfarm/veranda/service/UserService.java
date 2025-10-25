package com.smartfarm.veranda.service;

import com.smartfarm.veranda.dto.request.ChangePasswordRequest;
import com.smartfarm.veranda.dto.request.LoginRequest;
import com.smartfarm.veranda.dto.request.SignupRequest;
import com.smartfarm.veranda.dto.request.UpdateUserRequest;
import com.smartfarm.veranda.dto.response.LoginResponse;
import com.smartfarm.veranda.dto.response.SignupResponse;
import com.smartfarm.veranda.dto.response.UserResponse;
import com.smartfarm.veranda.entity.User;
import com.smartfarm.veranda.repository.UserRepository;

import com.smartfarm.veranda.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    // 중간에 에러나면 자동 롤백을 위해 트랜잭션으로 개발
    @Transactional // 저장하니까 기본으로
    public SignupResponse signup(SignupRequest request) {
        // 1.이메일 중복 체크
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 2. User 엔티티 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .nickname(request.getNickname())
                .build();

        // 3. DB 저장
        User savedUser = userRepository.save(user);

        // 4. 응답 DTO 생성
        return SignupResponse.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .nickname(savedUser.getNickname())
                .createdAt(savedUser.getCreatedAt())
                .message(("회원가입이 완료되었습니다."))
                .build();
    }

    // 로그인 (JWT 토큰 발급)
    @Transactional(readOnly = true) // 읽기 전용
    public LoginResponse login(LoginRequest request) {
        // 1. 이메일로 User 찾기
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // 2. 비밀번호 비교, 암호화 비밀번호 비교
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 토큰 생성
        String token = jwtUtil.generateToken(user.getUserId());

        // 4. LoginResponse 반환
        return LoginResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .token(token)
                .build();
    }

    // 회원정보 조회
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        // 1. userId로 User 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원 없음"));

        // 2. User -> UserResponse 변환
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // 회원정보 수정
    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        // 1. User 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원 없음"));

        // 2. 닉네임 변경
        user.setNickname(request.getNickname());

        // 3. 저장
        User updateUser = userRepository.save(user);

        // 4. UserResponse로 변환
        return UserResponse.builder()
                .userId(updateUser.getUserId())
                .email(updateUser.getEmail())
                .nickname(updateUser.getNickname())
                .createdAt(updateUser.getCreatedAt())
                .updatedAt(updateUser.getUpdatedAt())
                .build();
    }

    // 비밀번호 변경
    @Transactional
    public UserResponse changePassword(Long userId, ChangePasswordRequest request) {
        // 1. User 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원 없음"));

        // 2. 현재 비밀번호 맞는지 확인 (암호화된 비밀번호 비교)
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 새 비밀번호로 변경 (비밀번호 확인)
        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("새 비밀번호가 일치하지 않습니다.");
        }

        // 4. 새 비밀번호 암호화해서 저장
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User updateUser = userRepository.save(user);

        // 5. UserResponse로 변환
        return UserResponse.builder()
                .userId(updateUser.getUserId())
                .email(updateUser.getEmail())
                .nickname(updateUser.getNickname())
                .createdAt(updateUser.getCreatedAt())
                .updatedAt(updateUser.getUpdatedAt())
                .build();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {
        // 1. 회원이 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원 없음"));

        // 2. 삭제 실행
        userRepository.delete(user);
    }
}
