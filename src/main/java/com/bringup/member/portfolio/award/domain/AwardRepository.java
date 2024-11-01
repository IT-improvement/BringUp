package com.bringup.member.portfolio.award.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<AwardEntity, Integer> {
}
