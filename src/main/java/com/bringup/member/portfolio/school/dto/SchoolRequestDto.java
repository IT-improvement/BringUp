package com.bringup.member.portfolio.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SchoolRequestDto {

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
