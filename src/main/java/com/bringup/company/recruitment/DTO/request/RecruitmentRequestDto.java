package com.bringup.company.recruitment.DTO.request;

import com.bringup.common.enums.RecruitmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequestDto {
    private RecruitmentType recruitmentType; // 정규직, 비정규직, 아르바이트 중 하나
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private String status;
    private String recruitmentClass;
}