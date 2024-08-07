package com.bringup.member.user.domain.repository;

import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUserEmail(String userEmail);
    Optional<UserEntity> findByUserEmail(String userEmail);
}
