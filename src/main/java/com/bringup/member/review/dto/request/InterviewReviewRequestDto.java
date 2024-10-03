package com.bringup.member.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InterviewReviewRequestDto {
    private int companyIndex;
    private int userIndex;
    private String ambience;
    private int difficulty;
    private String interviewReviewTitle;
    private String interviewReviewContent;
}