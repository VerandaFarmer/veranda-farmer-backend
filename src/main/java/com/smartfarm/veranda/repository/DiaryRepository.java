package com.smartfarm.veranda.repository;

import com.smartfarm.veranda.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // jpa 기본 기능 사용
}
