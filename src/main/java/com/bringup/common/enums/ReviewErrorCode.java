package com.bringup.common.enums;

import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewErrorCode implements BaseErrorCode {
    NOT_VALID_TOKEN(400, "토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_REVIEW(400, "리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_WRITING(400, "게시글/댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    ReviewErrorCode(int errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.status = status;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}
