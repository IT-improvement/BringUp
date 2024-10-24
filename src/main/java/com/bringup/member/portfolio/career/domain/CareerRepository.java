package com.bringup.member.portfolio.career.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity,Integer> {
    List<CareerEntity> findByUserIndex(int userIndex);
    Boolean existsByUserIndexAndCompanyNameAndCareerDepartmentAndCareerPositionAndCareerStartAndCareerEnd(int userIndex, String companyName,String careerDepartment, String careerPosition, String careerStart, String careerEnd);
}
