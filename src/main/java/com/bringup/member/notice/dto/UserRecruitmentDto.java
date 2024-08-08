package com.bringup.member.notice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class UserRecruitmentDto {
    private int recruitmentIndex;
    private BigInteger companyId;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private String status;
    private String recruitmentClass;

}
