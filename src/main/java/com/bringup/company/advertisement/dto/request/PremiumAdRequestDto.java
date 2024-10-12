package com.bringup.company.advertisement.dto.request;

import com.bringup.company.advertisement.enums.Ad_Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PremiumAdRequestDto {
    private int recruitmentIndex; // 공고 인덱스
    private Ad_Type adType; // 광고 타입 (GP, P1, P2, P3) enum 사용
    private String timeSlot; // 노출 시간대
    private LocalDate startDate; // 사용자가 지정한 광고 시작 날짜
    private LocalDate endDate; // 사용자가 지정한 광고 종료 날짜
    private String display_startDate; // 광고 표출 시작 날짜
    private String display_endDate; // 광고 표출 종료 날짜
}
