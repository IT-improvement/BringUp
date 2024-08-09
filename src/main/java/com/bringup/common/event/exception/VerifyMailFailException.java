package com.bringup.common.event.exception;

import com.bringup.common.enums.CertificateErrorCode;
import lombok.Getter;

@Getter
public class VerifyMailFailException extends RuntimeException{
    private final CertificateErrorCode errorCode;

    public VerifyMailFailException(CertificateErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
