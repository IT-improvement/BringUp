package com.bringup.admin.user.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {
    private final MemberErrorCode errorCode;

    public AdminException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}