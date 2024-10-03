package com.bringup.member.review.contoller;

import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.member.review.dto.request.InterviewReviewRequestDto;
import com.bringup.member.review.dto.response.InterviewReviewResponseDto;
import com.bringup.member.review.exception.MemberReviewException;
import com.bringup.member.review.service.InterviewReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/interview")
public class MemberInterviewReviewController {

    private final InterviewReviewService interviewReviewService;
    private final ErrorResponseHandler errorResponseHandler; // 예외 처리를 위한 핸들러


    // 리뷰 리스트
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

    // 리뷰 작성
    @PostMapping("m_iv_create")
    public ResponseEntity<BfResponse<?>> createReview(@RequestBody InterviewReviewRequestDto requestDto) {
        try {
            interviewReviewService.createReview(requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 작성되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<BfResponse<?>> updateReview(
            @PathVariable int reviewId,
            @RequestBody InterviewReviewRequestDto requestDto) {
        try {
            interviewReviewService.updateReview(reviewId, requestDto);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 수정되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BfResponse<?>> deleteReview(@PathVariable int reviewId) {
        try {
            interviewReviewService.deleteReview(reviewId);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "인터뷰 리뷰가 성공적으로 삭제되었습니다."));
        } catch (MemberReviewException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
}