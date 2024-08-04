package com.bringup.company.recruitment.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequestDto {
    private String managerEmail;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private String status;
    private String recruitmentClass;
}