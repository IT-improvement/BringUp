package com.bringup.company.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentMainResponseDto {
    private String r_title;
    private String r_category;
    private String r_requirement;
    private String r_career;
    private String r_period;
}
