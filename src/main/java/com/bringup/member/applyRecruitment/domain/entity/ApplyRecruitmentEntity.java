package com.bringup.member.applyRecruitment.domain.entity;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
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
    @Column(name = "apply_cv_index")
    private Integer applyCVIndex;

    @Column(name = "cv_index")
    private int cvIndex;

    @Column(name = "recruitment_index", nullable = false)
    private int recruitmentIndex;

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
