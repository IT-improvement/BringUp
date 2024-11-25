package com.bringup.member.recruitment.domain.repository;

import com.bringup.member.recruitment.domain.entity.ScrapRecuritmentEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bringup.company.recruitment.entity.Recruitment;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRecruitmentRepository extends JpaRepository<ScrapRecuritmentEntity, Integer> {

    @Query("SELECT s.recruitmentIndex FROM ScrapRecuritmentEntity s WHERE s.userIndex.userIndex = :userIndex")
    List<Recruitment> findBookmarkedRecruitmentsByUserIndex(@Param("userIndex") int userIndex);

    // 특정 유저의 북마크된 공고 조회
    @Query("SELECT s FROM ScrapRecuritmentEntity s WHERE s.userIndex.userIndex = :userIndex")
    List<ScrapRecuritmentEntity> findByUserIndex(@Param("userIndex") Integer userIndex);

    // 특정 유저가 특정 공고를 북마크했는지 확인
    Optional<ScrapRecuritmentEntity> findByUserIndexAndRecruitmentIndex(UserEntity user, Recruitment recruitment);

}