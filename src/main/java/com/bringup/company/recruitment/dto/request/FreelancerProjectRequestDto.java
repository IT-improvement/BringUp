package com.bringup.company.recruitment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreelancerProjectRequestDto {
    private String projectTitle;            // 프로젝트 제목
    private String projectDescription;      // 프로젝트 설명
    private String expectedDuration;        // 예상 개발 기간
    private int expectedCost;               // 예상 비용
    private String requiredSkills;          // 필수 기술
    private String preferredSkills;         // 우대 기술
    private String workConditions;          // 근무 조건
}
