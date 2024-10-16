package com.bringup.member.applyRecruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.applyRecruitment.domain.entity.ApplyRecruitmentEntity;
import com.bringup.member.applyRecruitment.domain.service.ApplyRecruitmentService;
import com.bringup.member.applyRecruitment.dto.request.ApplyRecruitmentRequestDto;
import com.bringup.member.applyRecruitment.dto.response.ApplyRecruitmentResponseDto;
import com.bringup.member.applyRecruitment.exception.ApplyRecruitmentException;
import com.sun.net.httpserver.Authenticator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
public class ApplyRecruitmentController {
    private final ApplyRecruitmentService applyRecruitmentService;
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/mem/applyRecruitment")
    public ResponseEntity<BfResponse<?>> setApplyRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody ApplyRecruitmentRequestDto dto){
        try {
            applyRecruitmentService.setApplyRecruitment(userDetails, dto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "add recruitment successfully"));
        }catch (ApplyRecruitmentException e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
