package com.smartfarm.veranda.service;

import com.smartfarm.veranda.dto.request.DiaryCreateRequest;
import com.smartfarm.veranda.dto.request.DiaryUpdateRequest;
import com.smartfarm.veranda.dto.response.DiaryListResponse;
import com.smartfarm.veranda.dto.response.DiaryResponse;
import com.smartfarm.veranda.entity.Diary;
import com.smartfarm.veranda.entity.User;
import com.smartfarm.veranda.repository.DiaryRepository;
import com.smartfarm.veranda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 다이어리 작성
    @Transactional
    public DiaryResponse createDiary(DiaryCreateRequest request) {
        // 1. User 조회
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 2. Diary 생성
        Diary diary = Diary.builder()
                .user(user)
                .diaryDate(request.getDiaryDate())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .build();

        Diary saveDiary = diaryRepository.save(diary);

        return DiaryResponse.builder()
                .diaryId(saveDiary.getDiaryId())
                .userId(saveDiary.getUser().getUserId())
                .username(saveDiary.getUser().getNickname())
                .diaryDate(saveDiary.getDiaryDate())
                .content(saveDiary.getContent())
                .imageUrl(saveDiary.getImageUrl())
                .createdAt(saveDiary.getCreatedAt())
                .updatedAt(saveDiary.getUpdatedAt())
                .build();
    }

    // 전체 목록 조회
    @Transactional(readOnly = true)
    public List<DiaryListResponse> getAllDiaries(Long userId) {

        return diaryRepository.findByUser_UserId(userId).stream()
                .map(diary -> new DiaryListResponse(
                        diary.getDiaryId(),
                        diary.getDiaryDate(),
                        diary.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public DiaryResponse getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RuntimeException("다이어리를 찾을 수 없습니다."));

        return DiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .userId(diary.getUser().getUserId())
                .username(diary.getUser().getNickname())
                .diaryDate(diary.getDiaryDate())
                .content(diary.getContent())
                .imageUrl(diary.getImageUrl())
                .createdAt(diary.getCreatedAt())
                .updatedAt(diary.getUpdatedAt())
                .build();
    }

    // 수정
    @Transactional
    public DiaryResponse updateDiary(Long diaryId, Long userId, DiaryUpdateRequest request) {
        // 1. 다이어리 조회
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RuntimeException("다이어리를 찾을 수 없습니다."));

        // 본인 확인!
        if (!diary.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("본인의 다이어리만 수정할 수 있습니다.");
        }

        // 2. null이 아닐 때만 업데이트
        // content
        if(request.getContent() != null) {
            diary.setContent(request.getContent());
        }
        // IMG
        if(request.getImageUrl() != null) {
            diary.setImageUrl(request.getImageUrl());
        }

        // 3. 저장
        Diary saveDiary = diaryRepository.save(diary);

        // 4. 반환
        return DiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .userId(diary.getUser().getUserId())
                .username(diary.getUser().getNickname())
                .diaryDate(diary.getDiaryDate())
                .content(diary.getContent())
                .imageUrl(diary.getImageUrl())
                .createdAt(diary.getCreatedAt())
                .updatedAt(diary.getUpdatedAt())
                .build();
    }

    // 삭제
    @Transactional
    public void deleteDiary(Long diaryId, Long userId) {
        // 1. 다이어리 존제하는지 확인
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RuntimeException("다이어리를 찾을 수 없습니다."));

        // 본인 확인!
        if (!diary.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("본인의 다이어리만 삭제할 수 있습니다.");
        }

        // 2. 삭제
        diaryRepository.delete(diary);
    }
}
