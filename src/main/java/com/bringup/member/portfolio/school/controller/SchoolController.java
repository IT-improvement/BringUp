package com.bringup.member.portfolio.school.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.school.domain.SchoolService;
import com.bringup.member.portfolio.school.dto.SchoolListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
