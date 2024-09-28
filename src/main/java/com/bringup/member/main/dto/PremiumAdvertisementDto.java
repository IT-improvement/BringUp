package com.bringup.member.main.dto;

import com.bringup.common.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PremiumAdvertisementDto {
    private int premiumId;
    private int advertisementIndex;
    private String adType;
    private int price;
    private String timeSlot;
    private boolean isSoldOut;
    private LocalDate startDate;
    private LocalDate endDate;
    private String premiumImage;
}
