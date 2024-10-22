package com.bringup.member.applyRecruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.applyRecruitment.domain.service.ApplyRecruitmentFreelancerService;
import com.bringup.member.applyRecruitment.dto.request.ApplyFreelancerRequestDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
public class ApplyRecruitmentFreelancerController {
    private final ApplyRecruitmentFreelancerService applyRecruitmentFreelancerService;
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/mem/applyFreelancer")
    public ResponseEntity<BfResponse<?>> setApplyRecruitmentFreelancer(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ApplyFreelancerRequestDto dto){
        try {
            applyRecruitmentFreelancerService.setApplyRecruitmentFreelancer(userDetails, dto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "apply recruitment freelancer successfully"));
        }catch (ApplyRecruitmentException e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
