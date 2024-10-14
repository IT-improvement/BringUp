package com.bringup.member.companylist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyListResponseDto {
    private int companyId;
    private String companyName;
    private String companyScale;
    private String companyCategory;
    private String companyLogo;
}
