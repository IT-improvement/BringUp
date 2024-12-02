package com.bringup.member.recruitment.domain.repository;

import com.bringup.member.recruitment.domain.entity.RecruitmentVisit;
import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentVisitRepository extends JpaRepository<RecruitmentVisit, Integer> {
    boolean existsByUserAndRecruitmentIndex(UserEntity user, int recruitmentIndex);

    List<RecruitmentVisit> findByUser(UserEntity user);
}
