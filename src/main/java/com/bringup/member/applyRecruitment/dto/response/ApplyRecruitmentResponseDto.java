package com.bringup.member.applyRecruitment.dto.response;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.resume.domain.entity.CVEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRecruitmentResponseDto {
    private CVEntity cvEntity;
    private Recruitment recruitment;
    private ApplicationType applicationType;
    private ApplyCVType status;
    private String applyCVDate;
}
