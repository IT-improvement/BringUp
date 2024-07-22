package com.bringup.company.member.DTO.request;

import lombok.Builder;

@Builder
public record ValidationRequestDto(
        String businessNumber, // 사업자번호
        String startDate, // 개업일자
        String name, // 대표자 성명
        String name2, // 대표자성명2
        String businessName, // 상호
        String corporationNumber, // 법인등록번호
        String businessSector, // 주업태명
        String businessType, // 주종목명
        String businessAddress // 사업장주소
) {
}
