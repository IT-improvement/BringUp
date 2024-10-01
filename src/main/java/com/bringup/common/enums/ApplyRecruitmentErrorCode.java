package com.bringup.common.enums;

import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplyRecruitmentErrorCode implements BaseErrorCode{
    // 400 BAD_REQUEST
    BAD_REQUEST(400, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_FORMAT(400,"잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_MEMBER_CV(400,"이력서가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_APPLY_RECRUITMENT(400,"공고가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    // 404 NOT_FOUND
    NOT_FOUND_MEMBER_ID(404,"존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
    // 500 INTERNAL_SERVER_ERROR

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    ApplyRecruitmentErrorCode(int errorCode, String errorMessage, HttpStatus status){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public ErrorResponse getErrorResponse(){
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}
