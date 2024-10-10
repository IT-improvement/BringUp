package com.bringup.common.enums;

import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OrderErrorCode implements BaseErrorCode {
    // 404 NOT_FOUND
    NOT_FOUND_ORDER(404, "해당 주문이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // 400 BAD_REQUEST
    CANCEL_ORDER(400, "결제가 취소되었습니다.", HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    OrderErrorCode(int errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.status = status;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}
