package com.bringup.member.applyRecruitment.domain.repository;

import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRecruitmentRepository extends JpaRepository<ApplyRecruitmentEntity, Integer> {
}
