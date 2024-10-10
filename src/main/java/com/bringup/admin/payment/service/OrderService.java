package com.bringup.admin.payment.service;

import com.bringup.admin.payment.dto.request.PaymentConfirmRequestDto;
import com.bringup.admin.payment.dto.request.PaymentRequestDto;
import com.bringup.admin.payment.dto.response.PaymentConfirmResponseDto;
import com.bringup.admin.payment.dto.response.PaymentResponseDto;
import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.entity.Payment;
import com.bringup.admin.payment.enums.OrderStatus;
import com.bringup.admin.payment.exception.OrderException;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.admin.payment.repository.PaymentRepository;
import com.bringup.common.enums.BaseErrorCode;
import com.bringup.common.enums.OrderErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static com.bringup.common.enums.OrderErrorCode.CANCEL_ORDER;
import static com.bringup.common.enums.OrderErrorCode.NOT_FOUND_ORDER;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Value("${bootpay.application.key}")
    private String applicationId;

    @Value("${bootpay.private.key}")
    private String privateKey;

    private Bootpay bootpay;

    public void getBootpayToken() {
        try {
            bootpay = new Bootpay(applicationId, privateKey);
            HashMap token = bootpay.getAccessToken();
            if (token.get("error_code") != null) { // failed
                System.out.println("getAccessToken false: " + token);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaymentResponseDto createOrder(PaymentRequestDto paymentRequestDto, UserDetailsImpl userDetails) {
        Item item = itemRepository.findById(paymentRequestDto.getItemIdx())
                .orElseThrow(() -> new RuntimeException("해당 제품을 찾을 수 없습니다."));

        Company company = null;
        UserEntity userEntity = null;
        RolesType role = null;

        if (userDetails.getUserType().equals("COMPANY")) {
            role = RolesType.ROLE_COMPANY;
        } else {
            role = RolesType.ROLE_MEMBER;
        }

        Payment payment = new Payment();

        if (role.equals(RolesType.ROLE_COMPANY)) {
            company = companyRepository.findBycompanyId(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("해당 회사 정보를 찾을 수 없습니다."));
        } else {
            userEntity = userRepository.findByUserIndex(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("해당 사용자 정보를 찾을 수 없습니다."));
        }

        payment.setItemIdx(item);
        payment.setUserIdx(userDetails.getId());
        payment.setOrderStatus(OrderStatus.ORDER); // 초기 상태는 결제 대기
        payment.setRoles(role);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedOrder = paymentRepository.save(payment);

        if (company == null) {
            return PaymentResponseDto.builder()
                    .applicationId(applicationId)
                    .orderIndex(savedOrder.getOrderIndex())
                    .itemName(item.getItemName())
                    .orderStatus(savedOrder.getOrderStatus())
                    .roles(role)
                    .userId(userDetails.getId())
                    .userEmail(userEntity.getUserEmail())
                    .userName(userEntity.getUserName())
                    .phoneNumber(userEntity.getUserPhonenumber())
                    .price(item.getPrice())
                    .build();
        } else {
            return PaymentResponseDto.builder()
                    .applicationId(applicationId)
                    .orderIndex(savedOrder.getOrderIndex())
                    .itemName(item.getItemName())
                    .orderStatus(savedOrder.getOrderStatus())
                    .roles(role)
                    .userId(userDetails.getId())
                    .userEmail(company.getManagerEmail())
                    .userName(company.getCompanyName())
                    .phoneNumber(company.getManagerPhonenumber())
                    .price(item.getPrice())
                    .build();
        }
    }

    // 단건 조회
    public HashMap getBootpayReceipt(String receiptId) {
        try {
            getBootpayToken();
            HashMap res = bootpay.getReceipt(receiptId);
            if (res.get("error_code") == null) { // success
                System.out.println("getReceipt success: " + res);
            } else {
                System.out.println("getReceipt false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 결제 승인
    @Transactional
    public HashMap confirm(String receiptId){
        try {
            getBootpayToken();
            HashMap res = bootpay.confirm(receiptId);
            if(res.get("error_code") == null) { //success
                System.out.println("confirm success: " + res);

                // order테이블의 status column 데이터를 바꿔준다.
                Integer orderIdx = Integer.valueOf(res.get("order_id").toString());
                Optional<Payment> paymentOptional = paymentRepository.findByOrderIndex(orderIdx);
                if(!paymentOptional.isPresent()){
                    System.out.println("주문 번호에 해당하는 주문 정보가 없음.");
                    return null;
                }
                Payment payment = paymentOptional.get();
                payment.setOrderStatus(OrderStatus.COMPLETE);

            } else {
                System.out.println("confirm false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public ResponseEntity<?> priceCheck(PaymentConfirmRequestDto dto) {
        // 조회해서 영수증 받아오기
        HashMap res = getBootpayReceipt(dto.getReceiptId());

        // 영수증의 price와 order table의 price 가져오기
        Integer receiptPrice = Integer.valueOf(res.get("price").toString());

        Optional<Payment> paymentOptional = paymentRepository.findByOrderIndex(Integer.valueOf(res.get("order_id").toString()));
        // order 테이블에 해당 정보가 있는 지 확인
        if(paymentOptional.isEmpty()){
            System.out.println("주문 번호에 해당하는 주문 정보가 없음.");
            return null;
        }
        Payment payment = paymentOptional.get();

        Integer orderPrice = payment.getItemIdx().getPrice();

        System.out.println("orderPrice : " + orderPrice);
        System.out.println("receiptPrice : " + receiptPrice);

        // 두 값이 같으면
        if(receiptPrice.equals(orderPrice)){
            // confirm()
            HashMap resData = confirm(dto.getReceiptId());
            return ResponseEntity.ok(new BfResponse<>(resData));
        }
        // 아니면
        else {
            // order table에 status를 취소 상태로
            payment.setOrderStatus(OrderStatus.CANCEL);
            throw new OrderException(CANCEL_ORDER);
        }
    }
}