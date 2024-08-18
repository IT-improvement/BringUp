package com.bringup.admin.forcompany.exception;

import com.bringup.common.enums.AdminErrorCode;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {
    private final AdminErrorCode errorCode;

    public AdminException(AdminErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}