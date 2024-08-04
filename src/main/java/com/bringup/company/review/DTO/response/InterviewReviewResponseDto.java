package com.bringup.company.review.DTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewReviewResponseDto {
    private Integer reviewIndex;
    private String userEmail;
    private Integer ambience;
    private Integer difficulty;
    private String content;
    private String reviewTitle;
    private String reviewDate;
}
