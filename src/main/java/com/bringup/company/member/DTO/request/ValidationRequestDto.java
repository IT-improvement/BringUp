package com.bringup.company.member.DTO.request;

import lombok.Builder;

@Builder
public record ValidationRequestDto(
        String businessNumber, // 사업자번호
        String startDate, // 개업일자
        String name // 대표자 성명
) {
}
