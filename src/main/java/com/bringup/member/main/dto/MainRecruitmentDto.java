package com.bringup.member.main.dto;

import com.bringup.common.enums.StatusType;
import lombok.Data;

import java.math.BigInteger;

@Data
public class MainRecruitmentDto {
    private int recruitmentIndex;
    private BigInteger companyId;
    private String recruitmentTitle;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private int viewCount;
    private StatusType status;
    private String companyImg; // 회사 이미지
    private String companyName; // 회사 이미지

}
