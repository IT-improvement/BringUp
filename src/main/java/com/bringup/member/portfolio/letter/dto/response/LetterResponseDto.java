package com.bringup.member.portfolio.letter.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LetterResponseDto extends ResponseDto {

    private LetterResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<LetterResponseDto> success() {
        LetterResponseDto response = new LetterResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
