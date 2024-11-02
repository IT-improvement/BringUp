package com.bringup.member.portfolio.github.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GithubResponseDto extends ResponseDto {
    private GithubResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<GithubResponseDto> success() {
        GithubResponseDto response = new GithubResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
