package com.bringup.company.user.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class CompanyException extends RuntimeException {
    private final MemberErrorCode errorCode;

    public CompanyException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}