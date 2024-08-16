package com.bringup.member.portfolio.portfolio.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PortfolioInsertResponseDto extends ResponseDto {


    public PortfolioInsertResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<? super PortfolioInsertResponseDto> success(){
        PortfolioInsertResponseDto response = new PortfolioInsertResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
