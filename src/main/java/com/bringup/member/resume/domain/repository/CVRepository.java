package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CVEntity, Integer> {
    CVEntity findByCvIndex(int cvIndex);

    List<CVEntity> findAllByUserIndexAndStatus(int index,String status);
    
    Optional<CVEntity> findByUserIndex(int userIndex);

    Optional<CVEntity> findByUserIndexAndAndMainCvTrue(int userIndex);



}
