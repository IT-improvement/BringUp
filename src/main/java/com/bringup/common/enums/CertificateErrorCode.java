package com.bringup.common.enums;


import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CertificateErrorCode implements BaseErrorCode {
    MESSAGE_SEND_ERROR(500,"메세지 전송 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MAIL_SEND_ERROR(500,"메일 전송 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PHONE_NUMBER(400,"유효하지 않는 번호 형태입니다.",HttpStatus.BAD_REQUEST),
    INVALID_CERTIFCATE_NUMBER(404,"유효하지 않는 인증 번호입니다.",HttpStatus.NOT_FOUND);

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    CertificateErrorCode(int errorCode, String errorMessage, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}
