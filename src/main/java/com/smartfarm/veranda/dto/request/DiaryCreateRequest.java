package com.smartfarm.veranda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// 다이어리 작성
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryCreateRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId; // 누가 작성했는지

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate diaryDate;

    private String content;

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imageUrl;
}
