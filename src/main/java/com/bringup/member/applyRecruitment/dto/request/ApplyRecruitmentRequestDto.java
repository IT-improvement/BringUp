package com.bringup.member.applyRecruitment.dto.request;

import com.bringup.common.enums.RecruitmentStatusType;
import com.bringup.common.enums.RecruitmentType;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import lombok.Getter;

@Getter
public class ApplyRecruitmentRequestDto {
    private int cvIndex;
    private int recruitmentIndex;
    private RecruitmentStatusType recruitmentType;
    private ApplicationType applicationType;
}
