package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MainAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int exposureDays; // 노출 일수
    private LocalDate startDate; // 시작 날짜
}
