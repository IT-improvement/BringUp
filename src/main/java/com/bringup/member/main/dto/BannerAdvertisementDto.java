package com.bringup.member.main.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함한 생성자
public class BannerAdvertisementDto {
    private int recruitmentIndex;
    private String banner_Image; // 배너 이미지 경로
}
