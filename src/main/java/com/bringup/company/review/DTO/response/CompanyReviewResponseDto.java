package com.bringup.company.review.DTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyReviewResponseDto {
    private Integer reviewIndex;
    private String userEmail;
    private Integer advancement;
    private Integer benefit;
    private Integer workLife;
    private Integer companyCulture;
    private Integer management;
    private String content;
    private String reviewTitle;
    private String reviewDate;
}
