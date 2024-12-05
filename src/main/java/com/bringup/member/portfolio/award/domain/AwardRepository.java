package com.bringup.member.portfolio.award.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<AwardEntity, Integer> {

    boolean existsByOrganizationAndTitleAndAwarDate(String organization, String title, Date awardDate);
    List<AwardEntity> findByUserIndex(int userIndex);
    AwardEntity findById(int id);
}
