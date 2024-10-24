package com.bringup.member.portfolio.career.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.career.domain.CareerService;
import com.bringup.member.portfolio.career.dto.response.CareerListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequiredArgsConstructor
@RequestMapping("/portfolio/career")
public class CareerController {

    private final CareerService careerService;

    @GetMapping("/list")
    public ResponseEntity<? super CareerListResponseDto> getListCarrer(@AuthenticationPrincipal UserDetailsImpl userDetails){
        int userIndex = userDetails.getId();
        return careerService.getListCareer(userIndex);
    }
}
