package com.bringup.member.portfolio.portfolio.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    List<PortfolioEntity> findByUserIndex(int userIndex);
}
