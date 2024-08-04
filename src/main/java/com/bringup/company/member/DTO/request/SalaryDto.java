package com.bringup.company.member.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryDto {
    private String position;
    private int minSalary;
    private int maxSalary;
}
