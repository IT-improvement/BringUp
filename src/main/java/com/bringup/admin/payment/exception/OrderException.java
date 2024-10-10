package com.bringup.admin.payment.exception;

import com.bringup.common.enums.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderException extends RuntimeException{
    private final OrderErrorCode errorCode;

    public OrderException(OrderErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
