package com.smartfarm.veranda.repository;

import com.smartfarm.veranda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    // 이메일 중복 체크 (회원가입 시 사용)
    boolean existsByEmail(String email);

    // 이메일로 회원 찾기
    Optional<User> findByEmail(String email);
}
