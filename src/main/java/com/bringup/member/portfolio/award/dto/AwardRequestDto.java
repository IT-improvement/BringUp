package com.bringup.member.portfolio.award.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AwardRequestDto {
    private String title;
    private String organization;
    private Date awarDate;
    private String details;
}
