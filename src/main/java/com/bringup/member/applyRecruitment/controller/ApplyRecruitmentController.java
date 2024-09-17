package com.bringup.member.applyRecruitment.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.applyRecruitment.domain.service.ApplyRecruitmentService;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplyRecruitmentController {
    private final ApplyRecruitmentService applyRecruitmentService;

    @GetMapping("/mem/applyRecruitment/list")
    public ResponseEntity<List<ApplyRecruitmentResponseDto>> getApplyRecruitmentList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<ApplyRecruitmentResponseDto> applyRecruitmentList = applyRecruitmentService.getApplyRecruitmentList(userDetails);
        return ResponseEntity.ok(applyRecruitmentList);
    }
}
