package com.bringup.member.applyRecruitment.domain.repository;

import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRecruitmentRepository extends JpaRepository<ApplyRecruitmentEntity, Integer> {
    Optional<ApplyRecruitmentEntity> findByCvIndexAndRecruitmentIndex(int cvIndex, int recruitmentIndex);
}
