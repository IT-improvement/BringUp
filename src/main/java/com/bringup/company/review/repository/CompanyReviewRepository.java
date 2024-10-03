package com.bringup.company.review.repository;

import com.bringup.company.review.entity.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
    List<CompanyReview> findAllByCompanyCompanyId(int companyId);
    Optional<CompanyReview> findByCompanyReviewIndex(int reviewId);
}