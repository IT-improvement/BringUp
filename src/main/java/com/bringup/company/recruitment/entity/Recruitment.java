package com.bringup.company.recruitment.entity;

import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.enums.StatusType;
import com.bringup.company.user.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "recruitment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_index")
    private Integer recruitmentIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_index", nullable = false)
    private Company company;

    @Column(name = "recruitment_title", nullable = false, length = 30)
    private String recruitmentTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_type", nullable = false, length = 30)
    private RecruitmentType recruitmentType = RecruitmentType.REGULAR_WORKER; // 기본값 정규직

    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @Column(name = "skill", length = 30)
    private String skill;

    @Column(name = "career")
    private String career;

    @Column(name = "salary")
    private String salary;

    @Column(name = "work_detail")
    private String workDetail;

    @Column(name = "hospitality")
    private String hospitality;

    @Column(name = "start_date", nullable = false, length = 50)
    private String startDate;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "period", nullable = false, length = 30)
    private String period;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    }

