package com.bringup.member.portfolio.blog.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BlogInsertResponseDto extends ResponseDto {

    private BlogInsertResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
    }

    public static ResponseEntity<BlogInsertResponseDto> success() {
        BlogInsertResponseDto response = new BlogInsertResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<ResponseDto> existUrl(){
        ResponseDto response = new ResponseDto(ResponseCode.EXISTED_URL,ResponseMessage.EXISTED_URL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
