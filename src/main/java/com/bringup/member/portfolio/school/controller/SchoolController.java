package com.bringup.member.portfolio.school.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.school.domain.SchoolService;
import com.bringup.member.portfolio.school.dto.SchoolListResponseDto;
import com.bringup.member.portfolio.school.dto.SchoolRequestDto;
import com.bringup.member.portfolio.school.dto.SchoolResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/info/list")
    public ResponseEntity<? super SchoolListResponseDto> getSchoolList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userCode = userDetails.getId();
        return schoolService.getShcoolList(userCode);
    }

    @PostMapping("/insert")
    public ResponseEntity<? super SchoolResponseDto> insertSchool(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SchoolRequestDto schoolRequestDto) {
        int userCode = userDetails.getId();
        return schoolService.insertOrEditSchool(userCode, schoolRequestDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<? super SchoolResponseDto> deleteSchool(int schoolIndex){
        return schoolService.deleteSchool(schoolIndex);
    }
}
