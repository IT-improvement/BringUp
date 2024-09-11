package com.bringup.company.recruitment.dto.request;

import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.enums.StatusType;
import com.bringup.company.user.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequestDto {
    private RecruitmentType recruitmentType; // 정규직, 비정규직, 아르바이트 중 하나
    private String recruitmentTitle;
    private String category;
    private String skill;
    private String workDetail;
    private String hospitality;
    private String period;
    private int salary;
    private String career;
    private StatusType status;

}