package com.bringup.company.recruitment.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class RecruitmentException extends RuntimeException {
    private final MemberErrorCode errorCode;

    public RecruitmentException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}