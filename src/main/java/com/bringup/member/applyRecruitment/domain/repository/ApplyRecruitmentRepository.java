package com.bringup.member.applyRecruitment.domain.repository;

import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.resume.domain.entity.CVEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRecruitmentRepository extends JpaRepository<ApplyRecruitmentEntity, Integer> {
    Optional<ApplyRecruitmentEntity> findByCvIndexAndRecruitmentIndex(int cvIndex, int recruitmentIndex);
    List<ApplyRecruitmentEntity> findByCvIndex(int cvIndex);
}
