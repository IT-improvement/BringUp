package com.bringup.member.resume.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.resume.domain.entity.CVEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CVListResponseDto extends ResponseDto {

    private List<CVEntity> cvList;

    private CVListResponseDto(List<CVEntity> cvList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.cvList = cvList;
    }

    public static ResponseEntity<CVListResponseDto> success(List<CVEntity> cvList){
        CVListResponseDto response = new CVListResponseDto(cvList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
