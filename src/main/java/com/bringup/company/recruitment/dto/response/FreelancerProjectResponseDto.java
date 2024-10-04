package com.bringup.company.recruitment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreelancerProjectResponseDto {
    private Integer projectIndex;              // 프로젝트 ID
    private String projectTitle;            // 프로젝트 제목
    private String expectedDuration;        // 예상 개발 기간
    private int expectedCost;               // 예상 비용
}
