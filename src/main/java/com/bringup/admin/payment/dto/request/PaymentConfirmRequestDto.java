package com.bringup.admin.payment.dto.request;

import lombok.Data;

@Data
public class PaymentConfirmRequestDto {
    private String receiptId; // 부트페이에서 반환된 receiptId
}
