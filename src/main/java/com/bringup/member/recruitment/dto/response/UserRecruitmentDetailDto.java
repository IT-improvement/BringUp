package com.bringup.member.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecruitmentDetailDto {
    // 회사 관련 정보
    private String companyName;
    private String[] companyImg;  // 회사 이미지
    private String companyContent;  // 회사 소개 내용
    private String companyWelfare;  // 복지 혜택
    private String companyAddress;  // 근무 지역

    // 채용 공고 관련 정보
    private String recruitmentTitle;
    private String career;
    private String salary;
    private String recruitmentPeriod;
    private String requirements;
    private String hospitality;  // 우대 사항
    private String workDetail;   // 업무 소개

    private int applicantCount;  // 지원자 수
    private int minimumSalary;   // 최소 연봉
    private String deadline;     // 마감일
}
