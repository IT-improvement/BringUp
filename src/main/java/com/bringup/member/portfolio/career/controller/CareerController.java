package com.bringup.member.portfolio.career.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.career.domain.CareerService;
import com.bringup.member.portfolio.career.dto.request.CareerInsertRequestDto;
import com.bringup.member.portfolio.career.dto.response.CareerListResponseDto;
import com.bringup.member.portfolio.career.dto.response.CareerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public ResponseEntity<? super CareerResponseDto> insertCareer(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CareerInsertRequestDto careerInsertRequestDto){
        int userIndex = userDetails.getId();
        return careerService.insertCareer(userIndex, careerInsertRequestDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<? super CareerResponseDto> deleteCareer(@RequestParam String index){
        int careerIndex = Integer.parseInt(index);
        return careerService.deleteCareer(careerIndex);
    }
}
