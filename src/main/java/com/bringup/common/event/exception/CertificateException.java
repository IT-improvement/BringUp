package com.bringup.common.event.exception;

import com.bringup.common.enums.CertificateErrorCode;
import lombok.Getter;

@Getter
public class CertificateException extends RuntimeException{

    private final CertificateErrorCode errorCode;

    public CertificateException(CertificateErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
