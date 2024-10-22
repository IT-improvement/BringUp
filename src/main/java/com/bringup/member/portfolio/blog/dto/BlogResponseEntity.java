package com.bringup.member.portfolio.blog.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.blog.domain.BlogEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BlogResponseEntity extends ResponseDto {

    List<BlogEntity> list = null;

    private BlogResponseEntity(List<BlogEntity> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<BlogResponseEntity> success(List<BlogEntity> list) {
        BlogResponseEntity response = new BlogResponseEntity(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> noExistBlog(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_BLOG,ResponseMessage.NOT_EXISTED_BLOG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
