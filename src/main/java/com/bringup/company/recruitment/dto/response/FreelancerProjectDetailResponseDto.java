package com.bringup.company.recruitment.dto.response;

import com.bringup.common.enums.StatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class FreelancerProjectDetailResponseDto {
    private String c_logo;
    private String c_name;
    private String[] c_img;
    private String c_address;
    private Integer projectIndex;
    private String projectTitle;            // 프로젝트 제목
    private String projectDescription;      // 프로젝트 설명
    private String expectedDuration;        // 예상 개발 기간
    private int expectedCost;               // 예상 비용
    private String requiredSkills;          // 필수 기술
    private String preferredSkills;         // 우대 기술
    private String workConditions;          // 근무 조건
    private StatusType status;                  // 프로젝트 상태
    private LocalDate create_day;           // 생성 날짜
}
