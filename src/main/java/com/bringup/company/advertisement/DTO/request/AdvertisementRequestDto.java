package com.bringup.company.advertisement.DTO.request;

import lombok.Data;

@Data
public class AdvertisementRequestDto {
    private int advertisementIndex;
    private int recruitmentIndex;
    private String advertisementImage;
    private String type;
    private String displayTime;
}
