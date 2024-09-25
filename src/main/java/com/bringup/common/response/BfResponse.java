package com.bringup.common.response;


import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.enums.GlobalSuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;


@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BfResponse<T> {
    private int code;

    private String message;

    private T data;

    public BfResponse(T data) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }

    public BfResponse(GlobalSuccessCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    public BfResponse(GlobalSuccessCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    // 에러 응답을 위한 생성자
    public BfResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
