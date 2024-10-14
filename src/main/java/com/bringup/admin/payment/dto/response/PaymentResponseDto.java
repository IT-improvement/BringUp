package com.bringup.admin.payment.dto.response;

import com.bringup.admin.payment.enums.OrderStatus;
import com.bringup.common.enums.RolesType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private Integer orderIndex;       // 주문 인덱스
    private String itemName;          // 상품 이름
    private OrderStatus orderStatus;  // 주문 상태
    private RolesType roles;          // 역할 (기업 사용자 또는 개인 사용자)
    private Integer userId;           // 사용자 ID
    private String userName; // 회사 이름 또는 사용자 이름
    private int price;             // 결제 가격 정보
    private String userEmail;         // 사용자 이메일
    private String phoneNumber;     // 핸드폰 번호
    private String applicationId;     // 부트페이 애플리케이션 ID
}