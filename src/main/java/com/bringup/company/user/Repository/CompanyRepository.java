package com.bringup.company.user.Repository;

import com.bringup.company.user.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyPassword(String companyPassword);
    Optional<Company> findByManagerEmail(String managerEmail);

    boolean existsByManagerEmail(String managerEmail);

    boolean existsByManagerPhonenumber(String managerPhone);


}