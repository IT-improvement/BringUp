package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int durationDays; // 노출 일자
    private LocalDate startDate; // 시작 날짜
    private LocalDate endDate; // 끝 날짜
}
