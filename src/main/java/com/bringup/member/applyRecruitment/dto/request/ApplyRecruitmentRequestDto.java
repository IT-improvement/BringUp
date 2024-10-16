package com.bringup.member.applyRecruitment.dto.request;

import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import lombok.Getter;

@Getter
public class ApplyRecruitmentRequestDto {
    private int cvIndex;
    private int recruitmentIndex;
    private ApplicationType applicationType;
}
