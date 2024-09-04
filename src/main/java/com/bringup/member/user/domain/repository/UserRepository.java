package com.bringup.member.user.domain.repository;

import com.bringup.member.user.domain.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUserEmail(String userEmail);
    Optional<UserEntity> findByUserEmail(String userEmail);//Optional을 사용함으로써 null 값을 처리하는 데 따른 문제를 보다 명확하게 할 수 있음
    Optional<UserEntity> findByUserIndex(int userIndex);
}
