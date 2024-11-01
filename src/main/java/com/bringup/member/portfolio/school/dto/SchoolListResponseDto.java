package com.bringup.member.portfolio.school.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.school.domain.SchoolEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class SchoolListResponseDto extends ResponseDto {

    private SchoolEntity highSchool;
    private List<SchoolEntity> universityList;
    private List<SchoolEntity> graduateList;

    private SchoolListResponseDto(SchoolEntity highSchool, List<SchoolEntity> universityList, List<SchoolEntity> graduateList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.highSchool = highSchool;
        this.universityList = universityList;
        this.graduateList = graduateList;
    }

    public static ResponseEntity<SchoolListResponseDto> success(SchoolEntity highSchool, List<SchoolEntity> universityList, List<SchoolEntity> graduateList){
        SchoolListResponseDto response = new SchoolListResponseDto(highSchool, universityList, graduateList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
