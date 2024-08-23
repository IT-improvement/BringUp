package com.bringup.company.advertisement.dto.request;

import lombok.Data;

@Data
public class AdvertisementRequestDto {
    private Integer advertisementIndex;
    private Integer recruitmentIndex;
    private String advertisementImage;
    private String type;
    private String displayTime;
}
