package com.bringup.company.review.repository;

import com.bringup.company.review.entity.CompanyReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
    List<CompanyReview> findAllByCompanyCompanyId(int companyId);

    @Query("SELECT r FROM CompanyReview r WHERE r.company.companyId = :companyIdx " +
            "ORDER BY (r.advancement + r.benefit + r.workLife + r.companyCulture + r.management) / 5.0 DESC")
    List<CompanyReview> findTopByCompanyAverageRating(@Param("companyIdx") int companyIdx, Pageable pageable);

}