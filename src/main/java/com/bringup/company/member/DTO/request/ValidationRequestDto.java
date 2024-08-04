package com.bringup.company.member.DTO.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ValidationRequestDto{
    private String b_no; // 사업자번호
    private String start_dt; // 개업일자
    private String p_nm; // 대표자 성명
}
