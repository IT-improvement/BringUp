package com.bringup.company.recruitment.entity;

import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recruitment_freelancer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentFreelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_index")
    private Long projectIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_index", nullable = false)
    private Company company;  // 회사 정보와 연결

    @Column(name = "project_name", nullable = false, length = 100)
    private String projectName;

    @Column(name = "project_description", nullable = false, columnDefinition = "TEXT")
    private String projectDescription;

    @Column(name = "expected_duration", nullable = false, length = 50)
    private String expectedDuration;

    @Column(name = "expected_cost", nullable = false)
    private int expectedCost;

    @Column(name = "required_skills", nullable = false, length = 255)
    private String requiredSkills;

    @Column(name = "preferred_skills", length = 255)
    private String preferredSkills;

    @Column(name = "work_conditions", nullable = false, length = 255)
    private String workConditions;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "submission_deadline", nullable = false)
    private LocalDate submissionDeadline;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
