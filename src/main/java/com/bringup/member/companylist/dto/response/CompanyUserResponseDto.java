package com.bringup.member.companylist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUserResponseDto {
    private String email;
    private String cName;
    private String managerName;
    private String companyPhone;
    private String category;
    private String license;// 사업자 등록 번호
    private String address;
    private String welfare; // 복지
    private String vision;
    private String history;
    private String ceoName;
    private String managerPhone;
    private int cSize; // company_size (인간 수)
    private String logo;
}
