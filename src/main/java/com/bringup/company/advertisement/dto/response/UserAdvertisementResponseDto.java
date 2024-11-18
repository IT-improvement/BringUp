package com.bringup.company.advertisement.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAdvertisementResponseDto {
    private int adIdx;
    private String adType;
    private String recruitmentTitle;
    private String startDate;
    private String endDate;
    private int clickCount;
    private int payed;
}
