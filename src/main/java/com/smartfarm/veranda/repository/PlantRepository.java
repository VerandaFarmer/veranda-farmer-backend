package com.smartfarm.veranda.repository;

import com.smartfarm.veranda.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    // JPA 기본 메서드만 사용
}
