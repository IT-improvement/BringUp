package com.bringup.company.recruitment.dto.request;

import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.enums.StatusType;
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
    private String career;
    private String salary;
    private String workDetail;
    private String hospitality;
    private String requirement;
    private String period;
    private StatusType status;

}