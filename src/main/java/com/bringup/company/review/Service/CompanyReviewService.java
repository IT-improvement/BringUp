package com.bringup.company.review.Service;

import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.review.Entity.CompanyReview;
import com.bringup.company.review.Repository.CompanyReviewRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;
    private final UserRepository userRepository;

    public List<CompanyReview> getCompanyReviews(CompanyDetailsImpl userDetails) {
        return companyReviewRepository.findAllByCompanyCompanyId(userDetails.getId());
    }

    @Transactional
    public void deleteCompanyReview(CompanyDetailsImpl userDetails, Integer reviewIndex, String reason) {
        CompanyReview review = companyReviewRepository.findById(reviewIndex)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getUser().getUserEmail().equals(userDetails.getUsername())) {
            companyReviewRepository.delete(review);
            System.out.println("Deletion reason: " + reason);
        } else {
            throw new RuntimeException("Unauthorized to delete this review");
        }
    }
    //수정요청 ( 필요시 주석해제 )
    /*@Transactional
    public void updateCompanyReview(String token, Integer reviewIndex, String reason) {
        String username = jwtUtil.getUsername(token);
        CompanyReview review = companyReviewRepository.findById(reviewIndex)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getUser().getUserEmail().equals(username)) {
            // 여기서 수정 로직을 수행합니다. 예를 들어:
            // review.setContent(newContent);
            // companyReviewRepository.save(review);
            // 수정 사유를 로그로 남깁니다.
            System.out.println("Update reason: " + reason);
        } else {
            throw new RuntimeException("Unauthorized to update this review");
        }
    }*/
}
