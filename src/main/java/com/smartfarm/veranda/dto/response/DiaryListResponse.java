package com.smartfarm.veranda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryListResponse {
    private Long diaryId;
    private LocalDate diaryDate;
    private String imageUrl;
}
