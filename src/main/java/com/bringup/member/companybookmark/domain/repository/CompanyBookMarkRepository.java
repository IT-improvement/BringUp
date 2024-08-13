package com.bringup.member.companybookmark.domain.repository;

import com.bringup.member.companybookmark.domain.entity.CompanyBookMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyBookMarkRepository extends JpaRepository<CompanyBookMarkEntity, Integer> {
}
