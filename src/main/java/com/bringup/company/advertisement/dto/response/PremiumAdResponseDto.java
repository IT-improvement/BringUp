package com.bringup.company.advertisement.dto.response;

import com.bringup.common.enums.StatusType;
import com.bringup.company.advertisement.enums.Ad_Type;
import com.bringup.company.advertisement.enums.TimeSlot;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PremiumAdResponseDto {
    private int recruitmentIndex; // 공고 인덱스
    private int advertisementIndex;
    private StatusType status; // 현재 광고의 상태
    private Ad_Type adType; // 광고 타입 (GP, P1, P2, P3) enum 사용
    private TimeSlot timeSlot; // 노출 시간대 (enum 사용)
    private LocalDate startDate; // 광고 시작 날짜
    private LocalDate endDate; // 광고 종료 날짜
    private String ad_img; // 광고 이미지
    private Integer view_count; // 보여진 수
    private Integer click_count; // 클릭 수
}
