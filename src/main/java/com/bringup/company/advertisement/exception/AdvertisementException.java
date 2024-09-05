package com.bringup.company.advertisement.exception;

import com.bringup.common.enums.AdvertisementErrorCode;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.company.advertisement.entity.Advertisement;
import lombok.Getter;

@Getter
public class AdvertisementException extends RuntimeException {
    private final AdvertisementErrorCode errorCode;

    public AdvertisementException(AdvertisementErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}