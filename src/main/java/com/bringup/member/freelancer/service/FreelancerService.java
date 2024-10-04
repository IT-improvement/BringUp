package com.bringup.member.freelancer.service;

import com.bringup.common.response.ResponseDto;
import com.bringup.company.recruitment.entity.RecruitmentFreelancer;
import com.bringup.company.recruitment.repository.RecruitmentFreelancerRepository;
import com.bringup.member.freelancer.dto.FreelancerListResponseDto;
import com.bringup.member.freelancer.dto.FreelancerResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerService {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FreelancerService.class);
    private final RecruitmentFreelancerRepository recruitmentFreelancerRepository;

    public ResponseEntity<? super FreelancerListResponseDto> getRecruitmentFreelancerList() {
        List<RecruitmentFreelancer> list;
        try {
            list = recruitmentFreelancerRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("list error -->{}", e.toString());
            return ResponseDto.databaseError();
        }
        return FreelancerListResponseDto.success(list);
    }

    public ResponseEntity<? super FreelancerResponseDto> getRecruitmentFreelancerId(String index) {
        int indexInt = Integer.parseInt(index);
        RecruitmentFreelancer recruitmentFreelancer;
        try {
            recruitmentFreelancer = recruitmentFreelancerRepository.findByprojectIndex(indexInt);
            if(recruitmentFreelancer==null){
                return FreelancerResponseDto.noExistRecruitmentFreelancer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("list error -->{}", e.toString());
            return ResponseDto.databaseError();
        }
        return FreelancerResponseDto.success(recruitmentFreelancer);
    }
}
