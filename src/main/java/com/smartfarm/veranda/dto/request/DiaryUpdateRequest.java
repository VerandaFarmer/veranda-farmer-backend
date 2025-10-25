package com.smartfarm.veranda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 다이어리 수정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryUpdateRequest {

    private String content;

    private String imageUrl;
}
