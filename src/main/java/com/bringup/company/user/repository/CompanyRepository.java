package com.bringup.company.user.repository;

import com.bringup.company.user.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByCompanyPassword(String companyPassword);
    Optional<Company> findByManagerEmail(String managerEmail);

    boolean existsByManagerEmail(String managerEmail);

    boolean existsByManagerPhonenumber(String managerPhone);


}
