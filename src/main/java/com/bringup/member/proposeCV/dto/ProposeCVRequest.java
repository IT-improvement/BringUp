package com.bringup.member.proposeCV.dto;

import com.bringup.member.proposeCV.domain.ProposeCVEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProposeCVRequest {
    private String proposeTime;

    public ProposeCVEntity toEntity() {
        return ProposeCVEntity.builder()
                .proposeTime(proposeTime)
                .build();
    }
}
