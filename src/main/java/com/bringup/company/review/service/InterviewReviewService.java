package com.bringup.company.review.service;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.review.entity.InterviewReview;
import com.bringup.company.review.exception.ReviewException;
import com.bringup.company.review.repository.InterviewReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bringup.common.enums.ReviewErrorCode.NOT_FOUND_REVIEW;

@Service
@RequiredArgsConstructor
public class InterviewReviewService {

    private final InterviewReviewRepository interviewReviewRepository;

    // 면접 리뷰 조회
    public List<InterviewReview> getInterviewReviews(UserDetailsImpl userDetails) {
        return interviewReviewRepository.findAllByCompanyCompanyId(userDetails.getId());
    }

    // 면접 리뷰 삭제
    @Transactional
    public void deleteInterviewReview(UserDetailsImpl userDetails, Integer reviewIndex, String reason) {
        InterviewReview review = interviewReviewRepository.findById(reviewIndex)
                .orElseThrow(() -> new ReviewException(NOT_FOUND_REVIEW));
        if (review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            interviewReviewRepository.delete(review);
            System.out.println("삭제 사유: " + reason);
        } else {
            throw new ReviewException(NOT_FOUND_REVIEW);
        }
    }
}