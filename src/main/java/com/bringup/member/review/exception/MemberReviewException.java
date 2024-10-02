package com.bringup.member.review.exception;

import com.bringup.common.enums.ReviewErrorCode;
import lombok.Getter;

@Getter
public class MemberReviewException extends RuntimeException {
    private final ReviewErrorCode errorCode;

    public MemberReviewException(ReviewErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}