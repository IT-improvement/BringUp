package com.bringup.company.recruitment.Repository;

import com.bringup.company.recruitment.Entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    List<Recruitment> findAllByCompanyCompanyId(Long companyId);
    List<Recruitment> findAllByPeriod(String period);

    List<Recruitment> findAllByStatus(String status);
}