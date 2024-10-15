package com.bringup.member.applyRecruitment.domain.entity;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.resume.domain.entity.CVEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "apply_cv")
public class ApplyRecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cv_index", nullable = false)
    private CVEntity cvIndex;

    @ManyToOne
    @JoinColumn(name = "recruitment_index", nullable = false)
    private Recruitment recruitmentIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type", nullable = false)
    private ApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplyCVType status = ApplyCVType.IN_PROGRESS;

    @Column(name = "apply_cv_date", nullable = false, updatable = false)
    private LocalDateTime applyCVDate;

    @PrePersist
    protected void onCreate(){
        this.applyCVDate = LocalDateTime.now();
    }
}
