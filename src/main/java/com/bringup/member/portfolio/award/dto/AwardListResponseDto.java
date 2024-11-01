package com.bringup.member.portfolio.award.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.award.domain.AwardEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class AwardListResponseDto extends ResponseDto {

    List<AwardEntity> list;

    private AwardListResponseDto(List<AwardEntity> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<AwardListResponseDto> success(List<AwardEntity> list) {
        AwardListResponseDto response = new AwardListResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
