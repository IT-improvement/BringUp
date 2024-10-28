package com.bringup.member.applyRecruitment.dto.request;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRecruitmentResponseDto {
    private Integer applyCVIndex;
    private int cvIndex;
    private int recruitmentIndex;
    private ApplicationType applicationType;
    private ApplyCVType applyCVType;
    private LocalDateTime applyCVDate;
}
