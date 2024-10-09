package com.bringup.admin.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequestDto {
    private String itemName;
    private Integer price;
    private Integer itemIdx;
}