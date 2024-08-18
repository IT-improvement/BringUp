package com.bringup.member.companybookmark.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDto {
    private String managerEmail;
    private String companyName;
    private String companyCategory;
    private String companyPhoneNumber;
    private String companyAdress;
    private String companyLogo;
    private int companySize;
}
