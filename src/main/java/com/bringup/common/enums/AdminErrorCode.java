package com.bringup.common.enums;

import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AdminErrorCode implements BaseErrorCode {
    // 400 BAD_REQUEST
    BAD_REQUEST(400, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_RECRUITMENT(400, "존재하지 않는 공고입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_MEMBER_EMAIL(400, "이미 존재하는 회원 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_MEMBER_PHONE_NUMBER(400, "이미 등록된 휴대폰 번호입니다.", HttpStatus.BAD_REQUEST),
    DELETE_MEMBER(400, "탈퇴 또는 삭제된 회원입니다.", HttpStatus.BAD_REQUEST),
    // 403 FORBIDDEN
    FORBIDDEN_DELETE_MEMBER(403, "권한이 없습니다. 본인 계정만 탈퇴할 수 있습니다.", HttpStatus.FORBIDDEN),
    FORBIDDEN_RESET_PASSWORD(403, "권한이 없습니다. 본인 계정만 비밀번호 변경이 가능합니다.", HttpStatus.FORBIDDEN),

    // 404 NOT_FOUND
    NOT_FOUND_MEMBER_ID(404, "존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),

    // 500 INTERNAL_SERVER_ERROR
    BUSINESS_VALIDATE_ERROR(500, "사업자등록 진위여부 확인에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BUSINESS_STATUS_ERROR(500, "사업자등록번호 상태조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_UPLOAD_FAIL(500, "이미지 업로드에 실패했습니다. 관리자에게 문의해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);



    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    AdminErrorCode(int errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.status = status;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}