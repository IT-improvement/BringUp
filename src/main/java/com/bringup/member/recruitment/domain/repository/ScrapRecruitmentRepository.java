package com.bringup.member.recruitment.domain.repository;

import com.bringup.member.recruitment.domain.entity.ScrapRecuritmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bringup.company.recruitment.entity.Recruitment;
import java.util.List;

@Repository
public interface ScrapRecruitmentRepository extends JpaRepository<ScrapRecuritmentEntity, Integer> {

    @Query("SELECT s.recruitmentIndex FROM ScrapRecuritmentEntity s WHERE s.userIndex.userIndex = :userIndex")
    List<Recruitment> findBookmarkedRecruitmentsByUserIndex(@Param("userIndex") int userIndex);

}