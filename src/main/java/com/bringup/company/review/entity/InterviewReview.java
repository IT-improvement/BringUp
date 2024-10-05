package com.bringup.company.review.entity;

import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "interview_review")
public class InterviewReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_review_index")
    private Integer interviewReviewIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_index", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index", nullable = false)
    private UserEntity user;

    @Column(name = "ambience", nullable = false, columnDefinition = "int default 3")
    private int ambience;

    @Column(name = "difficulty", nullable = false, columnDefinition = "int default 3")
    private int difficulty;

    @Column(name = "interview_review_title", nullable = false, length = 30)
    private String interviewReviewTitle;

    @Column(name = "interview_review_date", nullable = false, length = 30)
    private String interviewReviewDate;

    @Column(name = "interview_review_content", length = 150)
    private String interviewReviewContent;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

}
