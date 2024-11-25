package com.bringup.member.applyRecruitment.domain.repository;

import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.resume.domain.entity.CVEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRecruitmentRepository extends JpaRepository<ApplyRecruitmentEntity, Integer> {
    boolean existsByCv_CvIndexAndRecruitmentIndex(Integer cvIndex, Integer recruitmentIndex);

    // 공고에 대한 지원자 수를 세기 위한 메서드 (일반 공고와 프리랜서 프로젝트에 대한 구분)
    int countByRecruitmentIndexAndApplicationType(int recruitmentIndex, ApplicationType applicationType);

    int countByRecruitmentIndex(int recruitmentIdx);
    
    List<ApplyRecruitmentEntity> findAllByCv_CvIndexAndApplicationType(Integer cvIndex, ApplicationType applicationType);

    List<ApplyRecruitmentEntity> findAllByCv_CvIndex(Integer cvIndex);
}
