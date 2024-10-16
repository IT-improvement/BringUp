package com.bringup.member.main.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private int recruitmentIndex; // 채용 공고의 고유 ID
    private String companyName; // 회사 이름
    private String recruitmentTitle; // 채용 공고 제목
    private String skill; // 요구 기술
    private String period; // 공고 기간 (종료 날짜)
    private String companyImg; // 회사 이미지 URL
}
