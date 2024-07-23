package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
}
