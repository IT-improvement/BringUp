package com.bringup.member.main.dto;

import com.bringup.common.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAdvertisementResponseDto {
    private Integer advertisementIndex;
    private String advertisementImage;
    private StatusType status;
    private String displayTime;
}
