package com.bringup.member.applyRecruitment.dto.response;

import com.bringup.common.enums.ApplyCVType;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRecruitmentResponseDto {
    private int cvIndex;
    private int recruitmentIndex;
    private ApplyCVType status;
    private String applyCVDate;

    public ApplyRecruitmentResponseDto(ApplyRecruitmentEntity applyRecruitmentEntity){
        this.cvIndex = applyRecruitmentEntity.getCvIndex();
        this.recruitmentIndex = applyRecruitmentEntity.getRecruitmentIndex();
        this.status = applyRecruitmentEntity.getStatus();
        this.applyCVDate = applyRecruitmentEntity.getApplyCVDate();
    }
}
