package com.bringup.member.board.exception;

import com.bringup.common.enums.BoardErrorCode;
import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException{
    private final BoardErrorCode errorCode;

    public BoardException(BoardErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
