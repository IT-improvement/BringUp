package com.bringup.member.portfolio.career.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CareerResponseDto extends ResponseDto {

    private CareerResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<CareerResponseDto> success() {
        CareerResponseDto response = new CareerResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> existCareer(){
        ResponseDto response = new ResponseDto(ResponseCode.EXISTED_CAREER,ResponseMessage.EXISTED_CAREER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
