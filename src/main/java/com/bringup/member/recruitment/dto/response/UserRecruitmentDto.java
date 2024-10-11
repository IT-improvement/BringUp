package com.bringup.member.recruitment.dto.response;

import com.bringup.common.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter

public class UserRecruitmentDto {
    private int recruitmentIndex;
    private String recruitmentTitle;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;

}
