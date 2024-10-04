package com.bringup.member.freelancer.dto;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class FreelancerListResponseDto extends ResponseDto {

    private List<RecruitmentFreelancer> list;

    public FreelancerListResponseDto(List<RecruitmentFreelancer> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.list = list;
    }

    public static ResponseEntity<FreelancerListResponseDto> success(List<RecruitmentFreelancer> list){
        FreelancerListResponseDto response = new FreelancerListResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
