package com.bringup.company.recruitment.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentResponseDto {
    private int recruitmentIndex;
    private String managerEmail;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private String status;
    private String recruitmentClass;
}
