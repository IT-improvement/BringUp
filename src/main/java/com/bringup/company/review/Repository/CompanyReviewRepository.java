package com.bringup.company.review.Repository;

import com.bringup.company.review.Entity.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
    List<CompanyReview> findAllByCompanyCompanyId(Long companyId);
}