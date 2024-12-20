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
    private int r_index;
    private String r_title;
    private String r_category;
    private String r_skill;
    private String r_career;
    private String r_period;
}
