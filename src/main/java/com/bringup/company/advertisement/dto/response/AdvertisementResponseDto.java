package com.bringup.company.advertisement.dto.response;

import com.bringup.common.enums.StatusType;
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
    private StatusType status;
}
