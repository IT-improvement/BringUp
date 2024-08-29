package com.bringup.member.companybookmark.domain.repository;

import com.bringup.member.companybookmark.domain.entity.CompanyBookMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyBookMarkRepository extends JpaRepository<CompanyBookMarkEntity, Integer> {
    List<CompanyBookMarkEntity> findByUserIndex(int userIndex);

    Optional<CompanyBookMarkEntity> findByUserIndexAndCompanyIndex(int userIndex, int companyIndex);
}
