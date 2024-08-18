package com.bringup.company.user.repository;

import com.bringup.company.user.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByCompanyId(Long companyId);

    Optional<Salary> findByCompanyIdAndPosition(Long companyId, String position);
}
