package com.bringup.member.resume.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.award.domain.AwardEntity;
import com.bringup.member.portfolio.blog.domain.BlogEntity;
import com.bringup.member.portfolio.career.domain.CareerEntity;
import com.bringup.member.portfolio.certificate.domain.CertificateEntity;
import com.bringup.member.portfolio.school.domain.SchoolEntity;
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
    private List<AwardEntity> cvAward;
    private List<BlogEntity> cvBlog;
    private List<CareerEntity> cvCareer;
    private List<CertificateEntity> cvCertificate;
    private List<SchoolEntity> cvSchool;

    private CVReadResponseDto(
            CVEntity cv, List<AwardEntity> cvAward, List<BlogEntity> cvBlog, List<CareerEntity> cvCareer, List<CertificateEntity> cvCertificate, List<SchoolEntity> cvSchool) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.cv = cv;
        this.cvAward = cvAward;
        this.cvBlog = cvBlog;
        this.cvCareer = cvCareer;
        this.cvCertificate = cvCertificate;
        this.cvSchool = cvSchool;
    }


    public static ResponseEntity<CVReadResponseDto> success(
            CVEntity cv, List<AwardEntity> cvAward, List<BlogEntity> cvBlog, List<CareerEntity> cvCareer, List<CertificateEntity> cvCertificate, List<SchoolEntity> cvSchool
    ){
        CVReadResponseDto response = new CVReadResponseDto(cv, cvAward, cvBlog, cvCareer, cvCertificate, cvSchool);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
