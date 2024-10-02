package com.bringup.member.review.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberCompanyReviewDto {

    private int companyReviewIndex; // 기업 리뷰 인덱스
    private int advancement; // 승진 기회
    private int benefit; // 복지 및 급여
    private int workLife; // 워라벨
    private int companyCulture; // 사내 문화
    private int management; // 경영진 평가
    private String content; // 기업 리뷰 내용
    private String companyReviewTitle; // 기업 리뷰 제목
    private String companyReviewDate; // 기업 리뷰 작성 날짜
}
