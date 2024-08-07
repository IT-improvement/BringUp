package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVPortfolioRepository extends JpaRepository<CVPortfolioEntity, Integer> {
}
