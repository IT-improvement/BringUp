package com.bringup.company.recruitment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyCvListResponseDto {
    private int applyCvIdx;
    private int cvIdx;
    private String type;
    private String status;
    private String applyDate;
}
