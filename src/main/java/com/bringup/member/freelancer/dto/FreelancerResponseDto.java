package com.bringup.member.freelancer.dto;

import com.bringup.common.enums.StatusType;
import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.user.entity.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FreelancerResponseDto extends ResponseDto {

    private Integer projectIndex;

    private Company company;

    private String projectTitle;

    private String projectDescription;

    private String expectedDuration;

    private int expectedCost;

    private String requiredSkills;

    private String preferredSkills;

    private String workConditions;

    private StatusType status = StatusType.CRT_WAIT;

    private LocalDate period;

    private LocalDateTime createdAt;

    private FreelancerResponseDto(RecruitmentFreelancer recruitmentFreelancer) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);

        this.projectIndex=recruitmentFreelancer.getProjectIndex();
        this.company=recruitmentFreelancer.getCompany();
        this.projectTitle=recruitmentFreelancer.getProjectTitle();
        this.projectDescription=recruitmentFreelancer.getProjectDescription();
        this.expectedDuration=recruitmentFreelancer.getExpectedDuration();
        this.expectedCost=recruitmentFreelancer.getExpectedCost();
        this.requiredSkills=recruitmentFreelancer.getRequiredSkills();
        this.preferredSkills=recruitmentFreelancer.getPreferredSkills();
        this.workConditions=recruitmentFreelancer.getWorkConditions();
        this.status=recruitmentFreelancer.getStatus();
        this.period=recruitmentFreelancer.getPeriod();
        this.createdAt=recruitmentFreelancer.getCreatedAt();
    }

    public static ResponseEntity<FreelancerResponseDto> success(RecruitmentFreelancer recruitmentFreelancer){
        FreelancerResponseDto response = new FreelancerResponseDto(recruitmentFreelancer);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> noExistRecruitmentFreelancer(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTEDT_RECRUITMENTFREELANCER,ResponseMessage.NOT_EXISTEDT_RECRUITMENTFREELANCER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
