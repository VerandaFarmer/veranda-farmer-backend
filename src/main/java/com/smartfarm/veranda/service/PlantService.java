package com.smartfarm.veranda.service;

import com.smartfarm.veranda.dto.response.PlantDetailResponse;
import com.smartfarm.veranda.dto.response.PlantListResponse;
import com.smartfarm.veranda.entity.Plant;
import com.smartfarm.veranda.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    // 식물 전체 조회 약식 버전
    @Transactional(readOnly = true)
    public List<PlantListResponse> getAllPlants() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream()
                .map(plant -> PlantListResponse.builder()
                        .plantId(plant.getPlantId())
                        .plantName(plant.getPlantName())
                        .difficulty(plant.getDifficulty())
                        .imageUrl(plant.getImageUrl())
                        .build())
                .toList();
    }

    // 식물 상세 정보 조회
    @Transactional(readOnly = true)
    public PlantDetailResponse getPlantById(Long plantId) {
        // 1, plantId 로 Plant 찾기
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new RuntimeException("식물 없음"));

        // 2. Plant -> PlantResponse 변환
        return PlantDetailResponse.builder()
                .plantId(plant.getPlantId())
                .plantName(plant.getPlantName())
                .scientificName(plant.getScientificName())
                .category(plant.getCategory())
                .description(plant.getDescription())
                .imageUrl(plant.getImageUrl())
                .optimalTemperature(plant.getOptimalTemperature())
                .optimalHumidity(plant.getOptimalHumidity())
                .optimalCo2(plant.getOptimalCo2())
                .optimalLightIntensity(plant.getOptimalLightIntensity())
                .ledColor(plant.getLedColor())
                .waterChangeInterval(plant.getWaterChangeInterval())
                .nutrientChangeInterval(plant.getNutrientChangeInterval())
                .growthSpeed(plant.getGrowthSpeed())
                .difficulty(plant.getDifficulty())
                .createdAt(plant.getCreatedAt())
                .updatedAt(plant.getUpdatedAt())
                .build();
    }
}
