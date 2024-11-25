package com.bringup.member.review.contoller;

import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.review.dto.request.InterviewReviewRequestDto;
import com.bringup.member.review.dto.response.InterviewReviewResponseDto;
import com.bringup.member.review.exception.MemberReviewException;
import com.bringup.member.review.service.MemberInterviewReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/interview")
public class MemberInterviewReviewController {

    private final MemberInterviewReviewService interviewReviewService;
    private final ErrorResponseHandler errorResponseHandler; // 예외 처리를 위한 핸들러



    // 인터뷰 리뷰 리스트
    @GetMapping("/iv_list")
    public ResponseEntity<BfResponse<?>> getAllReviews() {
        try {
            List<InterviewReviewResponseDto> reviews = interviewReviewService.getAllReviews();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviews));
        } catch (MemberReviewException e) {
            // 인터뷰 리뷰 관련 예외 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 인터뷰 리뷰 작성
    @PostMapping("/iv_create")
    public ResponseEntity<BfResponse<?>> createReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody InterviewReviewRequestDto requestDto) {
        try {
            interviewReviewService.createReview(userDetails, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 작성되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 인터뷰 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<BfResponse<?>> updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable("reviewId") int reviewId,
                                                      @RequestBody InterviewReviewRequestDto requestDto) {
        try {
            interviewReviewService.updateReview(userDetails, reviewId, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 수정되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 인터뷰 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<BfResponse<?>> deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable("reviewId") int reviewId) {
        try {
            interviewReviewService.deleteReview(userDetails, reviewId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 삭제되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }


    @GetMapping("/top-review/{companyIdx}")
    public ResponseEntity<BfResponse<?>> topReview(@PathVariable("companyIdx") int companyIdx){
        try{
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, interviewReviewService.getMostStar(companyIdx)));
        } catch (MemberReviewException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/company/{companyIdx}")
    public ResponseEntity<BfResponse<?>> getReviewsByCompany(@PathVariable("companyIdx") int companyIdx){
        List<InterviewReviewResponseDto> reviews = interviewReviewService.getAllReviewsByCompany(companyIdx);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviews));
    }
}
