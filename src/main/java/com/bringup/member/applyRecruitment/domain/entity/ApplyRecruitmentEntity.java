package com.bringup.member.applyRecruitment.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "apply_cv")
public class ApplyRecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cvIndex;
    private int recruitmentIndex;
    private String status;
    private String applyCVDate;
}
