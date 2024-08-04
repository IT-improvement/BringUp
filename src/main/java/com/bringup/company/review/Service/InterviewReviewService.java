package com.bringup.company.review.Service;

import com.bringup.common.jwt.JWTUtil;
import com.bringup.company.review.Entity.CompanyReview;
import com.bringup.company.review.Entity.InterviewReview;
import com.bringup.company.review.Repository.CompanyReviewRepository;
import com.bringup.company.review.Repository.InterviewReviewRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewReviewService {

    private final InterviewReviewRepository interviewReviewRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    // 면접 리뷰 조회
    public List<InterviewReview> getInterviewReviews(String token) {
        String username = jwtUtil.getUsername(token);
        UserEntity user = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return interviewReviewRepository.findAllByCompanyCompanyId((long) user.getUserIndex());
    }

    // 면접 리뷰 삭제
    @Transactional
    public void deleteInterviewReview(String token, Integer reviewIndex, String reason) {
        String username = jwtUtil.getUsername(token);
        InterviewReview review = interviewReviewRepository.findById(reviewIndex)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        if (review.getUser().getUserEmail().equals(username)) {
            // 여기서 삭제 로직을 수행합니다. 예를 들어:
            interviewReviewRepository.delete(review);
            // 삭제 사유를 로그로 남깁니다.
            System.out.println("삭제 사유: " + reason);
        } else {
            throw new RuntimeException("이 리뷰를 삭제할 권한이 없습니다.");
        }
    }
}