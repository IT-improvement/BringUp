package com.bringup.company.review.repository;

import com.bringup.company.review.entity.InterviewReview;
import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewReviewRepository extends JpaRepository<InterviewReview, Integer> {
    List<InterviewReview> findAllByCompanyCompanyId(int companyId);

    @Query("SELECT r FROM InterviewReview r WHERE r.company.companyId = :companyIdx ORDER BY (r.ambience + r.difficulty) / 2.0 DESC")
    List<InterviewReview> findTopByAverageRating(@Param("companyIdx") int companyIdx, Pageable pageable);

    List<InterviewReview> findAllByUser(UserEntity user);

}