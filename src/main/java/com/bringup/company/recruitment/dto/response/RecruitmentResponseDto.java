package com.bringup.company.recruitment.dto.response;

import com.bringup.common.enums.RecruitmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentResponseDto {
    private Integer recruitmentIndex;
    private String recruitmentTitle;
    private String managerEmail;
    private RecruitmentType recruitmentType; // 정규직, 비정규직, 아르바이트 중 하나
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private String status;
}
