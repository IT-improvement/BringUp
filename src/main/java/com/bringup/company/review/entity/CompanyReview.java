package com.bringup.company.review.entity;

import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_review")
public class CompanyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_review_index")
    private Integer companyReviewIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "advancement", nullable = false, columnDefinition = "int default 3")
    private int advancement;

    @Column(name = "benefit", nullable = false, columnDefinition = "int default 3")
    private int benefit;

    @Column(name = "work_life", nullable = false, columnDefinition = "int default 3")
    private int workLife;

    @Column(name = "company_culture", nullable = false, columnDefinition = "int default 3")
    private int companyCulture;

    @Column(name = "management", nullable = false, columnDefinition = "int default 3")
    private int management;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "company_review_title", nullable = false, length = 30)
    private String companyReviewTitle;

    @Column(name = "company_review_date", nullable = false, length = 30)
    private String companyReviewDate;

    @Column(name = "status", nullable = false, length = 10)
    private String status;
}
