package com.bringup.company.recruitment.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com/recruitment")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @PostMapping("/register")
    public ResponseEntity<BfResponse<?>> registerRecruitment(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                             @RequestBody RecruitmentRequestDto requestDto,
                                                             @RequestPart("recruitmentImg") MultipartFile img) {
        recruitmentService.createRecruitment(userDetails, requestDto, img);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment registration request submitted successfully"));
    }

    @PostMapping("/update/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> updateRecruitment(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                           @PathVariable Integer recruitmentId,
                                                           @RequestBody RecruitmentRequestDto requestDto,
                                                           @RequestPart MultipartFile img) {
        recruitmentService.updateRecruitment(userDetails, recruitmentId, requestDto, img);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment update request submitted successfully"));
    }

    @PostMapping("/delete/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> deleteRecruitment(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                           @PathVariable Integer recruitmentId,
                                                           @RequestBody String reason) {
        recruitmentService.deleteRecruitment(userDetails, recruitmentId, reason);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment deletion request submitted successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<BfResponse<List<RecruitmentResponseDto>>> listRecruitments(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        List<RecruitmentResponseDto> recruitments = recruitmentService.getRecruitments(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitments));
    }

    @GetMapping("/detail/{recruitmentId}")
    public ResponseEntity<BfResponse<RecruitmentResponseDto>> getRecruitmentDetail(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                                                   @PathVariable("recruitmentId") int recruitmentId) {
        RecruitmentResponseDto recruitmentDetail = recruitmentService.getRecruitmentDetail(userDetails, recruitmentId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitmentDetail));
    }
}