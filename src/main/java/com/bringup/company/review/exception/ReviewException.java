package com.bringup.company.review.exception;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.ReviewErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
    private final ReviewErrorCode errorCode;

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}