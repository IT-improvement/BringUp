package com.bringup.company.review.Controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.review.DTO.request.CompanyReviewRequestDto;
import com.bringup.company.review.DTO.request.InterviewReviewRequestDto;
import com.bringup.company.review.DTO.response.CompanyReviewResponseDto;
import com.bringup.company.review.DTO.response.InterviewReviewResponseDto;
import com.bringup.company.review.Entity.CompanyReview;
import com.bringup.company.review.Entity.InterviewReview;
import com.bringup.company.review.Service.CompanyReviewService;
import com.bringup.company.review.Service.InterviewReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    // 기업 리뷰 열람
    @PostMapping("/c_reviews")
    public ResponseEntity<BfResponse<List<CompanyReviewResponseDto>>> getCompanyReviews(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        List<CompanyReview> reviews = companyReviewService.getCompanyReviews(userDetails);
        List<CompanyReviewResponseDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviewDtos));
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

    // 기업 리뷰 삭제 요청
    @PostMapping("/c_review/delete")
    public ResponseEntity<BfResponse<?>> deleteCompanyReview(@AuthenticationPrincipal CompanyDetailsImpl userDetails, @RequestBody CompanyReviewRequestDto requestDto) {
        companyReviewService.deleteCompanyReview(userDetails, requestDto.getReviewIndex(), requestDto.getReason());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Review deletion request submitted successfully"));
    }

    // 기업 리뷰 수정 요청
    /*@PostMapping("/c_review/update")
    public ResponseEntity<BfResponse<?>> updateCompanyReview(@AuthenticationPrincipal CompanyDetailsImpl userDetails, @RequestBody CompanyReviewRequestDto requestDto) {
        companyReviewService.updateCompanyReview(token, requestDto.getReviewIndex(), requestDto.getReason());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Review update request submitted successfully"));
    }*/

    // 면접 리뷰 열람
    @PostMapping("/i_reviews")
    public ResponseEntity<BfResponse<List<InterviewReviewResponseDto>>> getInterviewReviews(@AuthenticationPrincipal CompanyDetailsImpl userDetails) {
        List<InterviewReview> reviews = interviewReviewService.getInterviewReviews(userDetails);
        List<InterviewReviewResponseDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviewDtos));
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

    // 면접 리뷰 삭제
    @PostMapping("/i_review/delete")
    public ResponseEntity<BfResponse<?>> deleteInterviewReview(@AuthenticationPrincipal CompanyDetailsImpl userDetails, @RequestBody InterviewReviewRequestDto requestDto) {
        interviewReviewService.deleteInterviewReview(userDetails, requestDto.getReviewIndex(), requestDto.getReason());
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Review deletion request submitted successfully"));
    }
}