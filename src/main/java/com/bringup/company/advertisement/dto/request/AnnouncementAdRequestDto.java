package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int durationMonths; // 노출 개월
    private LocalDate startDate; // 시작 날짜
}
