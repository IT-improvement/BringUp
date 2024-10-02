package com.bringup.company.user.repository;

import com.bringup.common.enums.StatusType;
import com.bringup.company.user.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByCompanyPassword(String companyPassword);
    Optional<Company> findByManagerEmail(String managerEmail);
    Optional<Company> findBycompanyId(int companyIndex);
    Optional<Company> findByCompanyName(String companyName);
    boolean existsByManagerEmail(String managerEmail);

    boolean existsByManagerPhonenumber(String managerPhone);
    // Status가 ACTIVE인 모든 회사 정보 조회
    List<Company> findAllByStatus(StatusType status);
}
