package com.bringup.member.portfolio.award.dto;

import com.bringup.member.portfolio.award.domain.AwardType;
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
    private AwardType awardType; // 추가된 필드

}
