package com.bringup.member.portfolio.blog.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.blog.domain.BlogEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class BlogReadResponseDto extends ResponseDto {

    List<BlogEntity> list = null;

    private BlogReadResponseDto(List<BlogEntity> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<BlogReadResponseDto> success(List<BlogEntity> list) {
        BlogReadResponseDto response = new BlogReadResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> noExistBlog(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_BLOG,ResponseMessage.NOT_EXISTED_BLOG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
