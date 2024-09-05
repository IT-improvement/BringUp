package com.bringup.company.recruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.RecruitmentRequestDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
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
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/register")
    public ResponseEntity<BfResponse<?>> registerRecruitment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody RecruitmentRequestDto requestDto,
                                                             @RequestPart("recruitmentImg") MultipartFile img) {
        try {
            recruitmentService.createRecruitment(userDetails, requestDto, img);
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
                                                           @RequestBody RecruitmentRequestDto requestDto,
                                                           @RequestPart MultipartFile img) {
        try {
            recruitmentService.updateRecruitment(userDetails, recruitmentId, requestDto, img);
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
    public ResponseEntity<BfResponse<?>> listRecruitments(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            System.out.println("UserDetails is null");
            return null;
        }

        try {
            List<RecruitmentResponseDto> recruitments = recruitmentService.getRecruitments(userDetails);
            if (recruitments == null) {
                System.out.println("Recruitments is null");
            } else {
                System.out.println("Number of recruitments: " + recruitments.size());
            }
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
            RecruitmentResponseDto recruitmentDetail = recruitmentService.getRecruitmentDetail(userDetails, recruitmentId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitmentDetail));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}