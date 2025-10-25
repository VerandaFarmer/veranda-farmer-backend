package com.smartfarm.veranda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryResponse {
    private Long diaryId;
    private Long userId;
    private String username;

    private LocalDate diaryDate;
    private String content;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
