package com.bringup.common.enums;

import com.bringup.common.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AdvertisementErrorCode implements BaseErrorCode {
    // 404 NOT_FOUND
    NOT_FOUND_ADVERTISEMENT(404, "광고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_RECRUITMENT(404, "채용 공고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_IMG_AND_DATE(404, "이미지와 일자가 모두 입력되지 않았습니다.", HttpStatus.NOT_FOUND),

    // 403 FORBIDDEN
    UNAUTHORIZED_ADVERTISEMENT_ACCESS(403, "이 광고에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 400 BAD_REQUEST
    INVALID_ADVERTISEMENT_DATA(400, "잘못된 광고 데이터입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_ACTIVE(400, "이미 진행중인 광고입니다.", HttpStatus.BAD_REQUEST),

    // 500 INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(500, "서버에 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    AdvertisementErrorCode(int errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.status = status;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.errorCode, this.errorMessage);
    }
}