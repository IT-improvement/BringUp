package com.bringup.member.board.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException{
    private final MemberErrorCode errorCode;

    public BoardException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
