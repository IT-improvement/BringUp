package com.bringup.member.kakao.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class KakaoUserResponseDto extends ResponseDto {
    private KakaoUserResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<KakaoUserResponseDto> success(){
        KakaoUserResponseDto response = new KakaoUserResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> existedEmail(){
        ResponseDto response = new ResponseDto(ResponseCode.EXISTED_EMAIL,ResponseMessage.EXISTED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
