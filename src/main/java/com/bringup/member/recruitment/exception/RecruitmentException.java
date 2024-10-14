package com.bringup.member.recruitment.exception;

import com.bringup.common.enums.RecruitmentErrorCode;
import lombok.Getter;

@Getter
public class RecruitmentException extends RuntimeException {
    private final RecruitmentErrorCode errorCode;

    public RecruitmentException(RecruitmentErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}