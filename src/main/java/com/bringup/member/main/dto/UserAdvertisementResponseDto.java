package com.bringup.member.main.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAdvertisementResponseDto {
    private Integer advertisementIndex;
    private String advertisementImage;
    private String type;
    private String displayTime;
}

