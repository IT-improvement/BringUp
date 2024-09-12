package com.bringup.company.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDetailResponseDto {
    private String c_logo;
    private String c_name;
    private String[] c_img;
    private String c_intro;
    private String c_welfare;
    private String c_address;
    private String r_title;
    private String r_workdetail;
    private String r_requirement;
    private String r_hospitality;
    private String r_career;
    private String r_salary;
    private String r_period;
}
