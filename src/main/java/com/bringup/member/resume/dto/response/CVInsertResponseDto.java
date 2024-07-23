package com.bringup.member.resume.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CVInsertResponseDto extends ResponseDto {

    public CVInsertResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<CVInsertResponseDto> success(){
        CVInsertResponseDto response = new CVInsertResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
