package com.bringup.member.portfolio.certificate.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CertificateResponseDto extends ResponseDto {

    private CertificateResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<CertificateResponseDto> success() {
        CertificateResponseDto response = new CertificateResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
