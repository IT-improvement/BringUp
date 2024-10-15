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

    @PostMapping("/mem/applyRecruitment/{recruitment_index}")
    public ResponseEntity<BfResponse<?>> addRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "recruitment_index") int recruitmentIndex, @Valid @RequestBody ApplyRecruitmentRequestDto dto){
        try {
            applyRecruitmentService.addRecruitment(userDetails, recruitmentIndex, dto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "add recruitment successfully"));
        }catch (ApplyRecruitmentException e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/mem/applyRecruitment")
    public ResponseEntity<BfResponse<?>> delRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam int cvIndex, @RequestParam int recruitmentIndex){
        try {
            applyRecruitmentService.delRecruitment(userDetails, cvIndex, recruitmentIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "delete apply recruitment successfully"));
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
