package com.bringup.company.advertisement.dto.response;

import com.bringup.common.enums.StatusType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MainAdResponseDto {
    private int mainId; // 메인광고 index
    private int recruitmentIndex; // 공고 index
    private int exposureDays; // 광고 일수
    private LocalDate startDate; // 시작날짜
    private LocalDate endDate; // 끝날짜
    private BigDecimal discountRate; // 할인율
    private StatusType status; // 광고 상태
    private String imageUrl; // 광고 이미지 URL
    private int viewCount; // 출력 수
    private int clickCount; // 클릭 수
}
