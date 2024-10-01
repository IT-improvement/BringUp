package com.bringup.member.main.dto;

import com.bringup.common.enums.StatusType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class PremiumAdvertisementDto {
    private int premiumId;
    private int advertisement;
    private int recruitmentIndex;  // 추가된 필드
    private String timeSlot;
    private LocalDate startDate;
    private LocalDate endDate;
    private String premiumImage;
}
