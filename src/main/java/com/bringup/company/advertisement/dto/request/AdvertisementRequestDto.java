package com.bringup.company.advertisement.dto.request;

import com.bringup.common.enums.StatusType;
import lombok.Data;

@Data
public class AdvertisementRequestDto {
    private Integer recruitmentIndex;
    private String type;
    private String displayTime;
}
