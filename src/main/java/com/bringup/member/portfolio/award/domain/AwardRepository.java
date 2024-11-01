package com.bringup.member.portfolio.award.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AwardRepository extends JpaRepository<AwardEntity, Integer> {

    boolean existsByOrganizationAndTitleAndAwarDate(String organization, String title, Date awardDate);

}
