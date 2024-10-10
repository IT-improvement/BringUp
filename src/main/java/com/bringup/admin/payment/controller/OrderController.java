package com.bringup.admin.payment.controller;

import com.bringup.admin.payment.dto.request.PaymentCompletionRequestDto;
import com.bringup.admin.payment.dto.request.PaymentRequestDto;
import com.bringup.admin.payment.dto.response.PaymentResponseDto;
import com.bringup.admin.payment.service.OrderService;
import com.bringup.common.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createOrder(@RequestBody PaymentRequestDto paymentRequestDto
                                                          //@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          ) {
        Integer userDetails = 19;
        PaymentResponseDto paymentResponseDto = orderService.createOrder(paymentRequestDto, userDetails);
        return ResponseEntity.ok(paymentResponseDto);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeOrder(@RequestBody PaymentCompletionRequestDto paymentCompletionDto) {
        orderService.completeOrder(paymentCompletionDto.getOrderId(), paymentCompletionDto.getReceiptId());
        return ResponseEntity.ok().build();
    }
}