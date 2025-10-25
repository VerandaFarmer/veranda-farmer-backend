package com.smartfarm.veranda.repository;

import com.smartfarm.veranda.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // jpa 기본 기능 사용

    // 특정 사용자의 다이어리만 조회
    List<Diary> findByUser_UserId(Long userId);
}
