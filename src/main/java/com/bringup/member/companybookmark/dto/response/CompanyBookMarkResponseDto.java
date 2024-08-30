package com.bringup.member.companybookmark.dto.response;

import com.bringup.member.companybookmark.domain.entity.CompanyBookMarkEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyBookMarkResponseDto{
    private int userIndex;
    private int companyIndex;
    private String status;

    public CompanyBookMarkResponseDto(CompanyBookMarkEntity companyBookMarkEntity){
        this.userIndex = companyBookMarkEntity.getUserIndex();
        this.companyIndex = companyBookMarkEntity.getCompanyIndex();
        this.status = companyBookMarkEntity.getStatus();
    }
}
