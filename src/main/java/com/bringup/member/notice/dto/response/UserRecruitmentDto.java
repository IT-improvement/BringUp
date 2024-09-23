package com.bringup.member.notice.dto.response;

import com.bringup.common.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter

public class UserRecruitmentDto {
    private int recruitmentIndex;
    private BigInteger companyId;
    private String recruitmentTitle;
    private String recruitmentType;
    private String category;
    private String skill;
    private String startDate;
    private String period;
    private int viewCount;
    private StatusType status;
    private int viewCount;
}
