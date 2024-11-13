package com.bringup.company.advertisement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BannerAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int exposureDays; // 노출 일수
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;
    private int orderIdx;
}
