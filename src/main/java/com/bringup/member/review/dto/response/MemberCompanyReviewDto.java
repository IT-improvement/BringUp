package com.bringup.member.review.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCompanyReviewDto {
    private int companyReviewIndex; // 기업 리뷰 인덱스
    private String companyName; // 회사 이름 (추가)
    private String userEmail; // 사용자 이메일 (추가)
    private String content; // 기업 리뷰 내용
    private String companyReviewTitle; // 기업 리뷰 제목
    private String companyReviewDate;
    private double AverageRating;

}
