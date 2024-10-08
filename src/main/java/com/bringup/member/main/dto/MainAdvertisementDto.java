package com.bringup.member.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainAdvertisementDto {
    private int recruitmentIndex;  // 추가된 필드
    private String main_Image; // 광고 메인 이미지 경로
}
