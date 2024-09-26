package com.bringup.member.applyRecruitment.exception;

import com.bringup.common.enums.ApplyRecruitmentErrorCode;
import com.bringup.common.enums.BaseErrorCode;
import com.bringup.common.enums.MemberErrorCode;

public class ApplyRecruitmentException extends RuntimeException{
    private final ApplyRecruitmentErrorCode errorCode;

    public ApplyRecruitmentException(ApplyRecruitmentErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
