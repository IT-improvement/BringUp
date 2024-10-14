package com.bringup.company.advertisement.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AvailableDatesResponseDto {
    private List<LocalDate> soldOutDates;    // 매진된 날짜 리스트  <--- 추가
    private BigDecimal discountRate;         // 할인율
    private BigDecimal finalPrice;           // 최종 가격

    public AvailableDatesResponseDto(List<LocalDate> soldOutDates, BigDecimal discountRate, BigDecimal finalPrice) {
        this.soldOutDates = soldOutDates;
        this.discountRate = discountRate;
        this.finalPrice = finalPrice;
    }
}
