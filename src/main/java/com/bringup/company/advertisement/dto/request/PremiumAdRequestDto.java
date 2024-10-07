package com.bringup.company.advertisement.dto.request;

import com.bringup.company.advertisement.enums.Ad_Type;
import com.bringup.company.advertisement.enums.TimeSlot;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PremiumAdRequestDto {
    private int recruitmentIndex; // 공고 인덱스
    private Ad_Type adType; // 광고 타입 (GP, P1, P2, P3) enum 사용
    private TimeSlot timeSlot; // 노출 시간대 (enum 사용)
    private LocalDate startDate; // 광고 시작 날짜
    private LocalDate endDate; // 광고 종료 날짜
}
