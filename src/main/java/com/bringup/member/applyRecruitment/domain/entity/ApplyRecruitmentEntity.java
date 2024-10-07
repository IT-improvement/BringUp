package com.bringup.member.applyRecruitment.domain.entity;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "apply_cv")
public class ApplyRecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cvIndex;
    private int recruitmentIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type", nullable = false)
    private ApplicationType applicationType;

    private ApplyCVType status = ApplyCVType.IN_PROGRESS;
    private String applyCVDate;
}
