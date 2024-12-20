package com.bringup.company.review.controller;

import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.dto.request.CompanyReviewRequestDto;
import com.bringup.company.review.dto.request.InterviewReviewRequestDto;
import com.bringup.company.review.dto.response.CompanyReviewResponseDto;
import com.bringup.company.review.dto.response.InterviewReviewResponseDto;
import com.bringup.company.review.entity.CompanyReview;
import com.bringup.company.review.entity.InterviewReview;
import com.bringup.company.review.exception.ReviewException;
import com.bringup.company.review.service.CompanyReviewService;
import com.bringup.company.review.service.InterviewReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class ReviewController {

    private final CompanyReviewService companyReviewService;
    private final InterviewReviewService interviewReviewService;
    private final ErrorResponseHandler errorResponseHandler; // 예외 처리를 위한 핸들러

    // 기업 리뷰 열람
    @PostMapping("/c_reviews")
    public ResponseEntity<BfResponse<?>> getCompanyReviews(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<CompanyReview> reviews = companyReviewService.getCompanyReviews(userDetails);
            List<CompanyReviewResponseDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviewDtos));
        } catch (ReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 기업 리뷰 삭제 요청
    @PostMapping("/c_review/delete")
    public ResponseEntity<BfResponse<?>> deleteCompanyReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CompanyReviewRequestDto requestDto) {
        try {
            companyReviewService.deleteCompanyReview(userDetails, requestDto.getReviewIndex(), requestDto.getReason());
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Review deletion request submitted successfully"));
        } catch (ReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 기업 리뷰 수정 요청
    /*@PostMapping("/c_review/update")
    public ResponseEntity<BfResponse<?>> updateCompanyReview(@AuthenticationPrincipal CompanyDetailsImpl userDetails, @RequestBody CompanyReviewRequestDto requestDto) {
        companyReviewService.updateCompanyReview(token, requestDto.getReviewIndex(), requestDto.getReason());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Review update request submitted successfully"));
    }*/

    // 면접 리뷰 열람
    @PostMapping("/i_reviews")
    public ResponseEntity<BfResponse<?>> getInterviewReviews(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<InterviewReview> reviews = interviewReviewService.getInterviewReviews(userDetails);
            List<InterviewReviewResponseDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviewDtos));
        } catch (ReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 면접 리뷰 삭제
    @PostMapping("/i_review/delete")
    public ResponseEntity<BfResponse<?>> deleteInterviewReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody InterviewReviewRequestDto requestDto) {
        try {
            interviewReviewService.deleteInterviewReview(userDetails, requestDto.getReviewIndex(), requestDto.getReason());
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "리뷰 삭제요청 전송완료"));
        } catch (ReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    private InterviewReviewResponseDto convertToDto(InterviewReview review) {
        return InterviewReviewResponseDto.builder()
                .reviewIndex(review.getInterviewReviewIndex())
                .userEmail(review.getUser().getUserEmail())
                .ambience(review.getAmbience())
                .difficulty(review.getDifficulty())
                .content(review.getInterviewReviewContent())
                .reviewTitle(review.getInterviewReviewTitle())
                .reviewDate(review.getInterviewReviewDate())
                .build();
    }

    private CompanyReviewResponseDto convertToDto(CompanyReview review) {
        return CompanyReviewResponseDto.builder()
                .reviewIndex(review.getCompanyReviewIndex())
                .userEmail(review.getUser().getUserEmail())
                .advancement(review.getAdvancement())
                .benefit(review.getBenefit())
                .workLife(review.getWorkLife())
                .companyCulture(review.getCompanyCulture())
                .management(review.getManagement())
                .content(review.getContent())
                .reviewTitle(review.getCompanyReviewTitle())
                .reviewDate(review.getCompanyReviewDate())
                .build();
    }
}