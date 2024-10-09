package com.bringup.admin.payment.dto.response;

import com.bringup.admin.payment.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDto {
    private Integer paymentIndex;
    private String itemName;
    private OrderStatus paymentStatus;
}