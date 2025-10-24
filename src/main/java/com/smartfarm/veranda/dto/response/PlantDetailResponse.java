package com.smartfarm.veranda.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantDetailResponse {
    private Long plantId;
    private String plantName;
    private String scientificName;

    private String category;
    private String description;
    private String imageUrl;

    private String optimalTemperature;
    private String optimalHumidity;
    private String optimalCo2;
    private String optimalLightIntensity;

    private String ledColor;
    private Integer waterChangeInterval;
    private Integer nutrientChangeInterval;

    private String growthSpeed;
    private String difficulty;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
