package com.bringup.admin.payment.service;

import com.bringup.admin.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private PaymentRepository paymentRepository;
    private Bootpay bootpay;

    @Value("${bootpay.application.key}")
    private String applicationId;

    @Value("${bootpay.private.key}")
    private String privateKey;

    //테스트용
    public void getBootpayToken(){
        try{
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
}
