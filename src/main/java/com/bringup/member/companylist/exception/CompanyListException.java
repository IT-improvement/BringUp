package com.bringup.member.companylist.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class CompanyListException extends RuntimeException{
    private final MemberErrorCode errorCode;

    public CompanyListException(MemberErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
