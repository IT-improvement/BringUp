package com.bringup.member.user.domain.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    private final MemberErrorCode errorCode;

    public MemberException(MemberErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
