package com.bringup.member.notice.domain.repository;

import com.bringup.member.notice.domain.entity.ScrapRecuritmentEntity;
import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRecruitmentRepository extends JpaRepository<ScrapRecuritmentEntity, Integer> {

    @Query("SELECT rb.recruitmentIndex FROM ScrapRecuritmentEntity rb WHERE rb.userIndex.userIndex = :userIndex")
    List<UserRecruitmentEntity> findBookmarkedRecruitmentsByUserIndex(@Param("userIndex") int userIndex);

}
