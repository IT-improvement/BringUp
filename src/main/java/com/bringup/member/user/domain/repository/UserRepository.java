package com.bringup.member.user.domain.repository;

import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUserEmail(String userEmail);
    Optional<UserEntity> findByUserEmail(String userEmail); //Optional을 사용함으로써 null 값을 처리하는 데 따른 문제를 보다 명확하게 할 수 있음
}
