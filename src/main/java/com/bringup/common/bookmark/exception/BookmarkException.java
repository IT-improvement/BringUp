package com.bringup.common.bookmark.exception;

import com.bringup.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class BookmarkException extends RuntimeException {
    private final MemberErrorCode errorCode;

    public BookmarkException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}