package com.bringup.company.advertisement.dto.response;

import com.bringup.common.enums.StatusType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnnouncementAdResponseDto {
    private int announcementId;
    private int recruitmentIndex; // 채용공고 인덱스
    private int durationMonths; // 노출 기간(개월)
    private StatusType status; // 광고 상태
    private LocalDate startDate; // 시작 날짜
    private LocalDate endDate; // 종료 날짜
}
