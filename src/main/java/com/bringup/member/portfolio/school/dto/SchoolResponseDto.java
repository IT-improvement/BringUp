package com.bringup.member.portfolio.school.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SchoolResponseDto extends ResponseDto {

    private SchoolResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<SchoolResponseDto> success() {
        SchoolResponseDto response = new SchoolResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
