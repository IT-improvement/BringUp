package com.bringup.member.applyRecruitment.controller;

import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.service.ApplyRecruitmentService;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bringup.common.response.ResponseCode.SUCCESS;

@RestController
@RequiredArgsConstructor
public class ApplyRecruitmentController {
    private final ApplyRecruitmentService applyRecruitmentService;
    private final ErrorResponseHandler errorResponseHandler;

    public ResponseEntity<BfResponse<?>> getApplyRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int recruitmentIndex){
        try {
            ApplyRecruitmentEntity applyRecruitment = applyRecruitmentService.getApplyRecruitment(userDetails, recruitmentIndex);
            return ResponseEntity.ok(new BfResponse<>(GlobalSuccessCode.valueOf(SUCCESS), applyRecruitment));
        }catch (ApplyRecruitmentException e){
            //return errorResponseHandler.handleErrorResponse(e.getErrorCode);
            return null;
        }
    }

    @GetMapping("/mem/applyRecruitment/{recruitment_index}")
    public ResponseEntity<BfResponse<?>> addApplyRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int recruitmentIndex){
        applyRecruitmentService.addApplyRecruitment(userDetails, recruitmentIndex);
        return ResponseEntity.ok(new BfResponse<>("이력서 지원 완료"));
    }

    @GetMapping("/mem/applyRecruitment/list")
    public ResponseEntity<List<ApplyRecruitmentResponseDto>> getApplyRecruitmentList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<ApplyRecruitmentResponseDto> applyRecruitmentList = applyRecruitmentService.getApplyRecruitmentList(userDetails);
        return ResponseEntity.ok(applyRecruitmentList);
    }
}
