package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BannerAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int exposureDays; // 노출 일수
    private String startDate;
    private String endDate;
}
