package com.bringup.common.event.exception;


import com.bringup.common.enums.BaseErrorCode;
import com.bringup.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import static com.bringup.common.enums.GlobalErrorCode.INTERNAL_SERVER_ERROR;
import static com.bringup.common.enums.GlobalErrorCode.VALIDATION_FAILED;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        log.error(">>>>> Internal Server Error : {}", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_SERVER_ERROR.getErrorResponse());
    }

    @ExceptionHandler(CertificateException.class)
    protected ResponseEntity<ErrorResponse> handleCertificationException(CertificateException ex) {
        log.error(">>>>> Internal Server Error : {}", ex);
        BaseErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(errorCode.getErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(">>>>> MethodArgumentNotValidException : {}", ex);
        ErrorResponse errorResponse = VALIDATION_FAILED.getErrorResponse();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorResponse.addValidation(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 메일 인증 실패 html error 페이지 반환
     */
    @ExceptionHandler(VerifyMailFailException.class)
    public ModelAndView handleCertificateException(VerifyMailFailException e) {
        ModelAndView modelAndView = new ModelAndView("mail/verify-fail");
        return modelAndView;
    }
}
