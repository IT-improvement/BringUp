package com.bringup.company.recruitment.controller;


import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.request.FreelancerProjectRequestDto;
import com.bringup.company.recruitment.dto.response.FreelancerProjectDetailResponseDto;
import com.bringup.company.recruitment.dto.response.FreelancerProjectResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.recruitment.service.FreelancerRecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com/freelancer")
public class FreelancerRecruitmentController {
    private final FreelancerRecruitmentService freelancerProjectService;
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/register")
    public ResponseEntity<BfResponse<?>> registerFreelancerProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                   @RequestBody FreelancerProjectRequestDto requestDto) {
        try {
            freelancerProjectService.createFreelancerProject(userDetails, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Freelancer project registration submitted successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<BfResponse<?>> updateFreelancerProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Integer projectId,
                                                                 @RequestBody FreelancerProjectRequestDto requestDto) {
        try {
            freelancerProjectService.updateFreelancerProject(userDetails, projectId, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Freelancer project updated successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{projectId}")
    public ResponseEntity<BfResponse<?>> deleteFreelancerProject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Integer projectId) {
        try {
            freelancerProjectService.deleteFreelancerProject(userDetails, projectId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Freelancer project deleted successfully"));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 프리랜서 프로젝트 리스트 전체 조회
    @GetMapping("/detail/list")
    public ResponseEntity<BfResponse<?>> listFreelancerProjects(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<FreelancerProjectDetailResponseDto> projects = freelancerProjectService.getFreelancerProjects(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, projects));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{projectId}")
    public ResponseEntity<BfResponse<?>> getFreelancerProjectDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable Integer projectId) {
        try {
            FreelancerProjectDetailResponseDto projectDetail = freelancerProjectService.getFreelancerProjectDetail(userDetails, projectId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, projectDetail));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
