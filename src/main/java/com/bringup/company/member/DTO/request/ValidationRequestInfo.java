package com.bringup.company.member.DTO.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidationRequestInfo{
    private String b_no;
    private String start_dt;
    private String p_nm;
    private String p_nm2;
    private String b_nm;
    private String corp_no;
    private String b_sector;
    private String b_type;
    private String b_adr;

    @Builder
    public ValidationRequestInfo(String b_no, String start_dt, String p_nm, String p_nm2, String b_nm, String corp_no,
                                 String b_sector, String b_type, String b_adr) {
        this.b_no = b_no;
        this.start_dt = start_dt;
        this.p_nm = p_nm;
        this.p_nm2 = p_nm2;
        this.b_nm = b_nm;
        this.corp_no = corp_no;
        this.b_sector = b_sector;
        this.b_type = b_type;
        this.b_adr = b_adr;
    }

    public static ValidationRequestInfo from(ValidationRequestDto dto) {
        return ValidationRequestInfo.builder()
                .b_no(dto.businessNumber())
                .start_dt(dto.startDate())
                .p_nm(dto.name())
                .p_nm2(dto.name2())
                .b_nm(dto.businessName())
                .corp_no(dto.corporationNumber())
                .b_sector(dto.businessSector())
                .b_type(dto.businessType())
                .b_adr(dto.businessAddress())
                .build();
    }
}