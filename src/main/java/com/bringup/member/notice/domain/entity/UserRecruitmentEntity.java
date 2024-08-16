package com.bringup.member.notice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@Table(name = "recruitment")
public class UserRecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_index")
    private int recruitmentIndex;

    @Column(name = "company_id")
    private BigInteger companyId;

    @Column(name = "recruitment_type")
    private String recruitmentType;

    @Column(name = "category")
    private String category;

    @Column(name = "skill")
    private String skill;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "period")
    private String period;

    @Column(name = "status")
    private String status;

    @Column(name = "recruitment_class")
    private String recruitmentClass;
}


