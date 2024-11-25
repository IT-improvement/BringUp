package com.bringup.member.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InterviewReviewResponseDto {
    private int interviewReviewIndex;
    private int ambience;
    private int difficulty;
    private String interviewReviewTitle;
    private String interviewReviewDate;
    private String interviewReviewContent;
    private String status;
}