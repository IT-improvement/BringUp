package com.bringup.admin.payment.dto.request;

import lombok.Data;

@Data
public class PaymentCompletionRequestDto {
    private Integer orderId;
    private String receiptId;
}
