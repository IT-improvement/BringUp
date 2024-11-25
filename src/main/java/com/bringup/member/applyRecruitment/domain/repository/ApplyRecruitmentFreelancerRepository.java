package com.bringup.member.applyRecruitment.domain.repository;

import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRecruitmentFreelancerRepository extends JpaRepository<ApplyRecruitmentEntity, Integer> {
}
