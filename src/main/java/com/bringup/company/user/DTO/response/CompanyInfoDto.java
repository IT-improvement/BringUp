package com.bringup.company.user.DTO.response;

import lombok.Builder;

@Builder
public record CompanyInfoDto(
        String email,
        String c_name,
        String manager_name,
        String company_phone,
        String category,
        String license, // 사업자 등록 번호
        String address,
        String welfare, // 복지
        String vision,
        String history,
        String ceo_name,
        String manager_phone,
        int c_size, // company_size (인간 수)
        int opencv,
        String logo
) {
}
