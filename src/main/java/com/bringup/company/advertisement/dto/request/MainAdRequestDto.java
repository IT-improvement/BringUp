package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class MainAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int exposureDays; // 노출 일수
    private String startDate; // 시작 날짜
    private String endDate; // 종료날짜
    private List<String> useDate;
}
