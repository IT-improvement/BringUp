package com.bringup.member.companybookmark.domain.repository;

import com.bringup.member.companybookmark.domain.entity.CompanyBookMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyBookMarkRepository extends JpaRepository<CompanyBookMarkEntity, Integer> {
    List<CompanyBookMarkEntity> findByUserIndexAndStatus(int userIndex, String status);

    Optional<CompanyBookMarkEntity> findByUserIndexAndCompanyIndex(int userIndex, int companyIndex);
}
