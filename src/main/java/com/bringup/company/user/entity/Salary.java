package com.bringup.company.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "company_index", nullable = false)
    private Integer companyId;

    @Column(name = "position", nullable = false, length = 10)
    private String position;

    @Column(name = "average_salary", nullable = false)
    private int averageSalary;

    @Column(name = "min_salary", nullable = false)
    private int minSalary;

    @Column(name = "max_salary", nullable = false)
    private int maxSalary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_index", insertable = false, updatable = false)
    private Company company;
}

