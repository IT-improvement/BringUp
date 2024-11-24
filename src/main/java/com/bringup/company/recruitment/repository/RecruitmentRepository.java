package com.bringup.company.recruitment.repository;

import com.bringup.common.enums.StatusType;
import com.bringup.company.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    List<Recruitment> findAllByCompanyCompanyId(int companyId);
    
    List<Recruitment> findAllByPeriod(String period);

    List<Recruitment> findAllByStatus(String status);

    Optional<Recruitment> findByCompanyCompanyId(int companyId);

    Optional<Recruitment> findByRecruitmentIndex(int recruitmentIndex);

    List<Recruitment> findAllByStatus(StatusType status);

    List<Recruitment> findTop100ByOrderByViewCountDesc();

}