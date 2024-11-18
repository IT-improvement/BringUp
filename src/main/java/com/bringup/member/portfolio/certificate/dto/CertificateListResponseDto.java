package com.bringup.member.portfolio.certificate.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.certificate.domain.CertificateEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CertificateListResponseDto extends ResponseDto {

    private List<CertificateEntity> list;

    private CertificateListResponseDto(List<CertificateEntity> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<CertificateListResponseDto> success(List<CertificateEntity> list) {
        CertificateListResponseDto response = new CertificateListResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
