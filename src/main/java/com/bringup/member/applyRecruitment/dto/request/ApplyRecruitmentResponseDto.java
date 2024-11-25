package com.bringup.member.applyRecruitment.dto.request;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.enums.ApplicationType;
import com.bringup.member.resume.domain.entity.CVEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRecruitmentResponseDto {
    private Integer applyCVIndex;
    private CVEntity cv;
    private int recruitmentIndex;
    private ApplicationType applicationType;
    private ApplyCVType status;
    private LocalDateTime applyCVDate;
    private String companyName; //회사 이름
    private String recruitmentTitle; //공고 이름
}
