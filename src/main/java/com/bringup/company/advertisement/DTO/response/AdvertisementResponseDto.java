package com.bringup.company.advertisement.DTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementResponseDto {
    private int advertisementIndex;
    private int recruitmentIndex;
    private String advertisementImage;
    private String type;
    private String displayTime;
    private String status;
}
