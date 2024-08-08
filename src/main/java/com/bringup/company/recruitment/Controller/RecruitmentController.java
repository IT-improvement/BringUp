package com.bringup.company.recruitment.Controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.recruitment.DTO.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.DTO.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.Service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company/recruitment")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @PostMapping("/register")
    public ResponseEntity<BfResponse<?>> registerRecruitment(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                             @RequestBody RecruitmentRequestDto requestDto) {
        recruitmentService.createRecruitment(userDetails, requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment registration request submitted successfully"));
    }

    @PostMapping("/update/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> updateRecruitment(@AuthenticationPrincipal CompanyDetailsImpl userDetails,
                                                           @PathVariable Integer recruitmentId,
                                                           @RequestBody RecruitmentRequestDto requestDto) {
        recruitmentService.updateRecruitment(userDetails, recruitmentId, requestDto);
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
}