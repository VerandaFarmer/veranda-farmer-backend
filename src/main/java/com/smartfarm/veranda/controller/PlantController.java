package com.smartfarm.veranda.controller;

import com.smartfarm.veranda.dto.response.PlantDetailResponse;
import com.smartfarm.veranda.dto.response.PlantListResponse;
import com.smartfarm.veranda.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    // 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<PlantListResponse>> getAllPlants() {
        return ResponseEntity.status(HttpStatus.OK).body(plantService.getAllPlants());
    }

    // 식물 상세 조회
    @GetMapping("/{plantId}")
    public ResponseEntity<PlantDetailResponse> getPlantById(@PathVariable Long plantId) {
        return ResponseEntity.status(HttpStatus.OK).body(plantService.getPlantById(plantId));
    }
}
