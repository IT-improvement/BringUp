package com.bringup.member.main.dto;


import com.bringup.common.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainRecruitmentDto {
    private int recruitmentIndex; // 채용 공고의 고유 ID
    private BigInteger companyId; // 회사의 고유 ID
    private String companyName; // 회사 이름
    private String recruitmentTitle; // 채용 공고 제목
    private String recruitmentType; // 채용 공고 유형
    private String category; // 카테고리
    private String skill; // 요구 기술
    private String startDate; // 공고 시작 날짜
    private String period; // 공고 기간 (종료 날짜)
    private int viewCount; // 조회수
    private StatusType status; // 상태 (예: ACTIVE, INACTIVE 등)
    private String companyImg; // 회사 이미지 URL
}
