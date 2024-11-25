package com.bringup.company.recruitment.repository;

import com.bringup.common.enums.StatusType;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentFreelancerRepository extends JpaRepository<RecruitmentFreelancer, Integer> {
    // 특정 회사의 모든 프리랜서 프로젝트 조회
    List<RecruitmentFreelancer> findAllByCompanyCompanyId(Integer companyId);

    // 특정 상태의 모든 프로젝트 조회
    List<RecruitmentFreelancer> findAllByStatus(StatusType status);

    // 프로젝트 상태를 기준으로 검색
    List<RecruitmentFreelancer> findAllByStatusAndCompanyCompanyId(StatusType status, Integer companyId);

    //프로젝트 상세 정보
    RecruitmentFreelancer findByprojectIndex(int projectIndex);

    Optional<RecruitmentFreelancer> findByProjectIndex(int projectIndex);
}
