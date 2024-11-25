package com.bringup.member.review.contoller;

import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.dto.response.CompanyReviewResponseDto;
import com.bringup.member.review.dto.request.RequestCompanyReviewDto;
import com.bringup.member.review.dto.response.InterviewReviewResponseDto;
import com.bringup.member.review.dto.response.MemberCompanyReviewDto;
import com.bringup.member.review.dto.response.MemberDetailReviewDto;
import com.bringup.member.review.exception.MemberReviewException;
import com.bringup.member.review.service.MemberReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberReviewController {

    private final MemberReviewService memberReviewService;
    private final ErrorResponseHandler errorResponseHandler; // 예외 처리를 위한 핸들러


    //리뷰 전체 리스트
    @GetMapping("/m_reviews")
    public ResponseEntity<BfResponse<?>> getMemberReview() {
        try {
            List<MemberCompanyReviewDto> reviews = memberReviewService.getCompanyReviews();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviews));
        } catch (MemberReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
    //상세보기
    @GetMapping("/reviewDetail/{reviewId}")
    public ResponseEntity<BfResponse<?>> getReviewDetail(@PathVariable("reviewId")  int reviewId) {
        try {
            MemberDetailReviewDto review = memberReviewService.getReviewDetail(reviewId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, review));
        } catch (MemberReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }


    // 리뷰 작성
    @PostMapping("/m_create")
    public ResponseEntity<BfResponse<?>> createReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody RequestCompanyReviewDto reviewDto) {
        try {
            memberReviewService.createCompanyReview(userDetails,reviewDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "리뷰가 성공적으로 작성되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<BfResponse<?>> updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable("reviewId") int reviewId,
                                                      @RequestBody RequestCompanyReviewDto reviewDto) {
        try {
            memberReviewService.updateCompanyReview(userDetails,reviewId, reviewDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "리뷰가 성공적으로 수정되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<BfResponse<?>> deleteCompanyReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("reviewId") int reviewId) {
        try {
            // 리뷰 삭제 요청
            memberReviewService.deleteCompanyReview(userDetails, reviewId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "리뷰가 성공적으로 삭제되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/top-review/{companyId}")
    public ResponseEntity<BfResponse<?>> getTopAverageRatingReviewForCompany(@PathVariable("companyId") int companyId) {
        try {
            MemberCompanyReviewDto topReview = memberReviewService.getMostStar(companyId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, topReview));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/company-review/{companyIdx}")
    public ResponseEntity<BfResponse<?>> getReviewsByCompany(@PathVariable("companyIdx") int companyIdx){
        List<MemberCompanyReviewDto> reviews = memberReviewService.getAllReviewsByCompany(companyIdx);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviews));
    }

    @GetMapping("/company-review/myReview")
    public ResponseEntity<BfResponse<?>> getMyReviews(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MemberCompanyReviewDto> reviews = memberReviewService.getMyCompanyReviewList(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviews));
    }
}
