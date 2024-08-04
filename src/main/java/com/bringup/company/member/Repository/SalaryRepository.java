package com.bringup.company.member.Repository;

import com.bringup.company.member.Entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByCompanyId(Long companyId);

    Optional<Salary> findByCompanyIdAndPosition(Long companyId, String position);
}
