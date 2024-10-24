package com.bringup.member.portfolio.career.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CareerInsertRequestDto {
    private String careerStart;
    private String careerEnd;
    private String companyName;
    private String careerPosition;
    private String careerDepartment;
    private String careerWork;
}
