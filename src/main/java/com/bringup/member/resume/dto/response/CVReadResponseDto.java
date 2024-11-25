/*
package com.bringup.member.resume.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.resume.domain.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CVReadResponseDto extends ResponseDto {
    private CVEntity cv;
    private List<CVAward> cvAward;
    private List<CVBlog> cvBlog;
    private List<CVCareer> cvCareer;
    private List<CVCertificate> cvCertificate;
    private List<CVSchool> cvSchool;

    private CVReadResponseDto(
            CVEntity cv, List<CVAward> cvAward, List<CVBlog> cvBlog, List<CVCareer> cvCareer, List<CVCertificate> cvCertificate, List<CVSchool> cvSchool) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.cv = cv;
        this.cvAward = cvAward;
        this.cvBlog = cvBlog;
        this.cvCareer = cvCareer;
        this.cvCertificate = cvCertificate;
        this.cvSchool = cvSchool;
    }


    public static ResponseEntity<CVReadResponseDto> success(
            CVEntity cv, List<CVAward> cvAward, List<CVBlog> cvBlog, List<CVCareer> cvCareer, List<CVCertificate> cvCertificate, List<CVSchool> cvSchool
    ){
        CVReadResponseDto response = new CVReadResponseDto(cv, cvAward, cvBlog, cvCareer, cvCertificate, cvSchool);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
*/
