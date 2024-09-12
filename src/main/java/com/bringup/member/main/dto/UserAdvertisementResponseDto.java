package com.bringup.member.main.dto;

import com.bringup.common.enums.StatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAdvertisementResponseDto {
    private Integer advertisementIndex;
    private Integer recruitmentIndex;
    private String advertisementImage;
    private String type;
    private String displayTime;
    private StatusType status;
}
