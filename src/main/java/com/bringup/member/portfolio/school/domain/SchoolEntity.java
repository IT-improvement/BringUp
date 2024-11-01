package com.bringup.member.portfolio.school.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "school")
public class SchoolEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schoolIndex;
    private int userIndex;
    private String type;
    private String schoolName;
    private String location;
    private Date startDate;
    private Date endDate;
    private String startStatus;
    private String endStatus;
    private String department;
    private String major;
    private String double_major;
    private BigDecimal grade;
    private BigDecimal maxGrade;
}