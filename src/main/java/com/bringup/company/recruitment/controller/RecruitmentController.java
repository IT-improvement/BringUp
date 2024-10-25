package com.bringup.company.recruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.*;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.recruitment.service.RecruitmentService;
import com.bringup.company.user.exception.CompanyException;
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
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/register")
    public ResponseEntity<BfResponse<?>> registerRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody RecruitmentRequestDto requestDto) {
        try {
            recruitmentService.createRecruitment(userDetails, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment registration request submitted successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> updateRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PathVariable Integer recruitmentId,
                                                           @RequestBody RecruitmentRequestDto requestDto) {
        try {
            recruitmentService.updateRecruitment(userDetails, recruitmentId, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment update request submitted successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> deleteRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PathVariable Integer recruitmentId,
                                                           @RequestBody String reason) {
        try {
            recruitmentService.deleteRecruitment(userDetails, recruitmentId, reason);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Recruitment deletion request submitted successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<BfResponse<?>> listAllRecruitments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // 일반 공고 및 프리랜서 프로젝트 통합 리스트 가져오기
            List<UnifiedRecruitmentDto> unifiedRecruitments = recruitmentService.getAllRecruitments(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, unifiedRecruitments));
        } catch (Exception e) {
            // 에러 처리 (적절한 에러 핸들링 로직 추가)
            return ResponseEntity.internalServerError().body(new BfResponse<>(null, "Failed to retrieve recruitments"));
        }
    }

    @GetMapping("/detail/list")
    public ResponseEntity<BfResponse<?>> listRecruitments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<RecruitmentResponseDto> recruitments = recruitmentService.getRecruitments(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitments));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> getRecruitmentDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable("recruitmentId") int recruitmentId) {
        try {
            RecruitmentDetailResponseDto recruitmentDetail = recruitmentService.getRecruitmentDetail(userDetails, recruitmentId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitmentDetail));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/applyCvs")
    public ResponseEntity<BfResponse<?>> getRecruitmentApplyList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            List<ApplyCvListResponseDto> applyCvList = recruitmentService.getApplyCvs(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, applyCvList));
        } catch (RecruitmentException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (CompanyException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
}