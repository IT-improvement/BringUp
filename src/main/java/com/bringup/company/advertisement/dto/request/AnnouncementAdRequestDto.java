package com.bringup.company.advertisement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementAdRequestDto {
    private int recruitmentIndex; // 채용공고 인덱스
    private int durationDays; // 노출 일자
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate; // 시작 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate; // 끝 날짜
}
