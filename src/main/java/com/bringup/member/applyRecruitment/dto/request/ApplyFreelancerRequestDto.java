package com.bringup.member.applyRecruitment.dto.request;

import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import lombok.Getter;

@Getter
public class ApplyFreelancerRequestDto {
    private int cvIndex;
    private int projectIndex;
    private ApplicationType applicationType;
}
