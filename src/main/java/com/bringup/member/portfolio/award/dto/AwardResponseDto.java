package com.bringup.member.portfolio.award.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AwardResponseDto extends ResponseDto {

    private AwardResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<AwardResponseDto> success() {
        AwardResponseDto response = new AwardResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<ResponseDto> duplicateAward() {
        ResponseDto response = new ResponseDto(ResponseCode.EXISTED_AWARD,ResponseMessage.EXISTED_AWARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
