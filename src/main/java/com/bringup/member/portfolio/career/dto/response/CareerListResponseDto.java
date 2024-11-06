package com.bringup.member.portfolio.career.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.career.domain.CareerEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CareerListResponseDto extends ResponseDto {

    private List<CareerEntity> list = null;

    private CareerListResponseDto(List<CareerEntity> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<CareerListResponseDto> success(List<CareerEntity> list) {
        CareerListResponseDto response = new CareerListResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public static ResponseEntity<ResponseDto> noExistCarrer(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_CAREER,ResponseMessage.NOT_EXISTED_CAREER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
