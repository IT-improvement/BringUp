package com.bringup.company.review.Service;

import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.review.Entity.InterviewReview;
import com.bringup.company.review.Repository.InterviewReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewReviewService {

    private final InterviewReviewRepository interviewReviewRepository;

    // 면접 리뷰 조회
    public List<InterviewReview> getInterviewReviews(CompanyDetailsImpl userDetails) {
        return interviewReviewRepository.findAllByCompanyCompanyId(userDetails.getId());
    }

    // 면접 리뷰 삭제
    @Transactional
    public void deleteInterviewReview(CompanyDetailsImpl userDetails, Integer reviewIndex, String reason) {
        InterviewReview review = interviewReviewRepository.findById(reviewIndex)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        if (review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            interviewReviewRepository.delete(review);
            System.out.println("삭제 사유: " + reason);
        } else {
            throw new RuntimeException("이 리뷰를 삭제할 권한이 없습니다.");
        }
    }
}