package com.bringup.member.portfolio.portfolio.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    ArrayList findByUserIndex(String userIndex);
}
