package com.bringup.company.review.repository;

import com.bringup.company.review.entity.InterviewReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewReviewRepository extends JpaRepository<InterviewReview, Integer> {
    List<InterviewReview> findAllByCompanyCompanyId(Long companyId);
}