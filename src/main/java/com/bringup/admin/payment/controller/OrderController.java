package com.bringup.admin.payment.controller;

import com.bringup.admin.payment.dto.request.PaymentConfirmRequestDto;
import com.bringup.admin.payment.dto.request.PaymentRequestDto;
import com.bringup.admin.payment.dto.response.PaymentConfirmResponseDto;
import com.bringup.admin.payment.dto.response.PaymentResponseDto;
import com.bringup.admin.payment.exception.OrderException;
import com.bringup.admin.payment.service.OrderService;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
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
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/order")
    public ResponseEntity<BfResponse<PaymentResponseDto>> createOrder(@RequestBody PaymentRequestDto paymentRequestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        PaymentResponseDto paymentResponseDto = orderService.createOrder(paymentRequestDto, userDetails);
        System.out.println("리퀘스트" + paymentRequestDto);
        System.out.println("리스펀스" + paymentResponseDto);
        return ResponseEntity.ok(new BfResponse<>(paymentResponseDto));
    }

    @PostMapping("/bootpay/check")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmRequestDto paymentConfirmRequestDto) {
        try{
            return orderService.priceCheck(paymentConfirmRequestDto);
        } catch (OrderException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }

    /*@PostMapping("/complete")
    public ResponseEntity<Void> completeOrder(@RequestBody PaymentCompletionRequestDto paymentCompletionDto) {
        orderService.completeOrder(paymentCompletionDto.getOrderId(), paymentCompletionDto.getReceiptId());
        return ResponseEntity.ok().build();
    }*/
}