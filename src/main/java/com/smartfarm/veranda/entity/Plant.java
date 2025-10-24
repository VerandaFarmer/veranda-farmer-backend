package com.smartfarm.veranda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plant {

    // 기본정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantId; // 식물 id

    @Column(nullable = false, length = 100)
    private String plantName; // 식물 이름

    @Column(length = 100)
    private String scientificName; // 영문 학술명

    @Column(length = 50)
    private String category; // 카테고리

    @Column(columnDefinition = "TEXT")
    private String description; // 설명

    @Column(length = 255)
    private String imageUrl; // 이미지 url

    //환경설정 (센서 관련)
    @Column(length = 20)
    private String optimalTemperature; // 적정 온도

    @Column(length = 20)
    private String optimalHumidity; // 적정 습도

    @Column(length = 20)
    private String optimalCo2; // 적정 이산화탄소

    @Column(length = 50)
    private String optimalLightIntensity; // 적정 광량

    // LED 색상 관리, 물/양액 관리

    @Column(length = 50)
    private String ledColor; // LED 색상

    @Column
    private Integer waterChangeInterval; // 물 교환 주기

    @Column
    private Integer nutrientChangeInterval; // 양액 교환 주기

    // 재배 난이도
    @Column(length = 20)
    private String growthSpeed; // 재배 속도

    @Column(length = 20)
    private String difficulty;

    // 시간 정보
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
