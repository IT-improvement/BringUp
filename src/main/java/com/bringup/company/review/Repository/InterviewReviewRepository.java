package com.bringup.company.review.Repository;

import com.bringup.company.review.Entity.CompanyReview;
import com.bringup.company.review.Entity.InterviewReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewReviewRepository extends JpaRepository<InterviewReview, Integer> {
    List<InterviewReview> findAllByCompanyCompanyId(Long companyId);
}