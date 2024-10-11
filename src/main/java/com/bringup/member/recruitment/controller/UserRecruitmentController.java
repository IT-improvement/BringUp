package com.bringup.member.recruitment.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.dto.response.RecruitmentDetailResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.member.recruitment.domain.service.UserRecruitmentService;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDetailDto;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequestMapping("/recruitment")
@RequiredArgsConstructor
public class UserRecruitmentController {


    private final UserRecruitmentService userRecruitmentService;
    private final ErrorResponseHandler errorResponseHandler;


    @GetMapping("/list")
    public ResponseEntity<List<UserRecruitmentDto>> getAllRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getAllRecruitments();
        return ResponseEntity.ok(recruitments); // JSON 형식으로 반환
    }


    @GetMapping("/scrap")
    public ResponseEntity<List<UserRecruitmentDto>> getBookmarkedRecruitments() {
        List<UserRecruitmentDto> recruitments = userRecruitmentService.getBookmarkedRecruitments();
        return ResponseEntity.ok(recruitments);
    }

    @GetMapping("/detail/{recruitmentId}")
    public ResponseEntity<BfResponse<?>> getRecruitmentDetail(@PathVariable("recruitmentId") int recruitmentId) {
        try {
            UserRecruitmentDetailDto recruitmentDetail = userRecruitmentService.getRecruitmentDetail(recruitmentId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitmentDetail));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/topList")
    public ResponseEntity<BfResponse<?>> getTopRecruitments() {
        try {
            List<UserRecruitmentDto> recruitments = userRecruitmentService.getTopRecruitments();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, recruitments));
        }catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}