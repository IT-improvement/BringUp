package com.bringup.company.user.repository;

import com.bringup.company.user.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    Optional<Salary> findByCompanyId(int companyId);

    Optional<Salary> findByCompanyIdAndPosition(int companyId, String position);
}
