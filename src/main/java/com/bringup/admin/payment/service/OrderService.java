package com.bringup.admin.payment.service;

import com.bringup.admin.payment.dto.request.PaymentRequestDto;
import com.bringup.admin.payment.dto.response.PaymentDto;
import com.bringup.admin.payment.dto.response.PaymentResponseDto;
import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.entity.Payment;
import com.bringup.admin.payment.enums.OrderStatus;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.admin.payment.repository.PaymentRepository;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;

    public PaymentResponseDto createOrder(PaymentRequestDto paymentRequestDto, UserDetailsImpl userDetails) {
        Item item = itemRepository.findById(paymentRequestDto.getItemIdx())
                .orElseThrow(() -> new RuntimeException("해당 제품을 찾을 수 없습니다."));

        RolesType role = null;

        if(userDetails.getUserType().equals("COMPANY")){
            role = RolesType.ROLE_COMPANY;
        } else{
            role = RolesType.ROLE_MEMBER;
        }

        Payment payment = new Payment();
        payment.setItemIdx(item);
        payment.setUserIdx(userDetails.getId());
        payment.setOrderStatus(OrderStatus.ORDER); // 초기 상태는 결제 대기
        payment.setRoles(role);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedOrder = paymentRepository.save(payment);

        return new PaymentResponseDto(savedOrder.getOrderIndex(), item.getItemName(), savedOrder.getOrderStatus());
    }

    public void completeOrder(Integer paymentId, String receiptId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("해당 결제을 찾을 수 없습니다."));

        // 결제가 완료되면 결제 상태 업데이트 및 receiptId 저장
        payment.setOrderStatus(OrderStatus.COMPLETE);
        payment.setReceiptId(receiptId);
        paymentRepository.save(payment);
    }
}