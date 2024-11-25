package com.bringup.company.recruitment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AcceptCVResponseDto {
    private Integer cvIdx;
    private String cv_name;
    private String cv_history;
    private String cv_date;
}
