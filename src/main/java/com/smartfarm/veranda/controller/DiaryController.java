package com.smartfarm.veranda.controller;

import com.smartfarm.veranda.dto.request.DiaryCreateRequest;
import com.smartfarm.veranda.dto.request.DiaryUpdateRequest;
import com.smartfarm.veranda.dto.response.DiaryListResponse;
import com.smartfarm.veranda.dto.response.DiaryResponse;
import com.smartfarm.veranda.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // 다이어리 작성
    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request) {
        DiaryResponse response = diaryService.createDiary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 다이어리 전체 조회
    @GetMapping
    public ResponseEntity<List<DiaryListResponse>> getAllDiaries(@AuthenticationPrincipal Long userId) {
        List<DiaryListResponse> response = diaryService.getAllDiaries(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 다이어리 상세조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> getDiaryById(@PathVariable Long diaryId) {
        DiaryResponse response = diaryService.getDiaryById(diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 다이어리 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> updateDiary(@PathVariable Long diaryId, @AuthenticationPrincipal Long userId, @Valid @RequestBody DiaryUpdateRequest request) {
        DiaryResponse response = diaryService.updateDiary(diaryId, userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 다이어리 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId, @AuthenticationPrincipal Long userId) {
        diaryService.deleteDiary(diaryId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

// @AuthenticationPrincipal Long userId
//```
//
//**작동 원리:**
//```
//1. 클라이언트가 JWT 토큰과 함께 요청
//   ↓
//2. JwtAuthenticationFilter가 토큰 검증
//   ↓
//3. SecurityContext에 userId 저장
//   ↓
//4. @AuthenticationPrincipal이 자동으로 userId 가져옴
//   ↓
//5. Controller 메서드에서 사용!

// 즉, 토큰에서 자동으로 userId를 꺼내주는거
