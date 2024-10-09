package com.bringup.admin.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private String transactionId;  // Bootpay에서 반환된 결제 트랜잭션 ID
    private String itemName;       // 상품 이름
    private Integer price;         // 결제 금액
    private String applicationId;  // Bootpay 애플리케이션 ID
    private Integer userId;        // 사용자 ID (JWT에서 추출된 값)
}

