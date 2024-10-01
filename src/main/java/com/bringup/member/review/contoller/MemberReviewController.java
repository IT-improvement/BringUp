package com.bringup.member.review.contoller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.dto.response.CompanyReviewResponseDto;
import com.bringup.company.review.entity.CompanyReview;
import com.bringup.company.review.exception.ReviewException;
import com.bringup.member.review.service.MemberReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberReviewController {

    private final MemberReviewService memberReviewService;

  /*  // 기업 리뷰 열람
    @PostMapping("/m_reviews")
    public ResponseEntity<BfResponse<?>> getMemberReview(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<CompanyReview> reviews = memberReviewService.getCompanyReviews(userDetails);
            List<CompanyReviewResponseDto> reviewDtos = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, reviewDtos));
        } catch (ReviewException e) {
            // ReviewException 발생 시 처리
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
*/
}
