package com.bringup.member.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCompanyReviewDto {
    private String companyName;  // 회사 이름 (ID 대신)
    private int advancement;     // 승진 기회 평가
    private int benefit;         // 복지 및 급여 평가
    private int workLife;        // 워라벨 평가
    private int companyCulture;  // 사내 문화 평가
    private int management;      // 경영진 평가
    private String content;      // 리뷰 내용
    private String companyReviewTitle;  // 리뷰 제목
}
