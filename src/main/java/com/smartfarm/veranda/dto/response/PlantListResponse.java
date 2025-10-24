package com.smartfarm.veranda.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantListResponse {
    private Long plantId;
    private String plantName;
    private String difficulty;
    private String imageUrl;
}
