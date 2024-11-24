package com.bringup.company.recruitment.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.response.AcceptCVResponseDto;
import com.bringup.company.recruitment.service.AcceptRecruitmentService;
import com.bringup.company.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class AcceptRecruitmentController {


    private final AcceptRecruitmentService acceptRecruitmentService;

    @GetMapping("/volunteers")
    public ResponseEntity<BfResponse<List<AcceptCVResponseDto>>> getVolunteers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서비스 메서드 호출
        List<AcceptCVResponseDto> volunteers = acceptRecruitmentService.getVolunteerListInRecruitment(userDetails);

        // 결과 반환
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, volunteers));
    }
}
