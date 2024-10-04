package com.bringup.member.freelancer.controller;

import com.bringup.member.freelancer.dto.FreelancerListResponseDto;
import com.bringup.member.freelancer.dto.FreelancerResponseDto;
import com.bringup.member.freelancer.service.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freelancer")
@RequiredArgsConstructor
public class FreelancerController {

    private final FreelancerService freelancerService;

    @GetMapping("/list")
    public ResponseEntity<? super FreelancerListResponseDto> getRecruitmentFreelancerList(){
        ResponseEntity<? super FreelancerListResponseDto> response = freelancerService.getRecruitmentFreelancerList();
        return response;
    }

    @GetMapping("/info/{index}")
    public ResponseEntity<? super FreelancerResponseDto> getRecruitmentFreelancer(@PathVariable("index") String index){
        ResponseEntity<? super FreelancerResponseDto> response = freelancerService.getRecruitmentFreelancerId(index);
        return response;
    }
}
