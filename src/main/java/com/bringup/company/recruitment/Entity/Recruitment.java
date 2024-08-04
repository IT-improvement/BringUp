package com.bringup.company.recruitment.Entity;

import com.bringup.company.member.Entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recruitment")
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_index")
    private int recruitmentIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "recruitment_type", nullable = false, length = 30)
    private String recruitmentType;

    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @Column(name = "skill", length = 30)
    private String skill;

    @Column(name = "start_date", nullable = false, length = 50)
    private String startDate;

    @Column(name = "period", nullable = false, length = 30)
    private String period;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "recruitment_class", nullable = false, length = 30)
    private String recruitmentClass;
}
