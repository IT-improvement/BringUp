package com.bringup.common.event.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class CoolsmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.sender}")
    private String fromPhoneNumber;

    public void sendSms(String to, String token) throws CoolsmsException {
        try {

            Message coolsms = new Message(apiKey, apiSecret); // 생성자를 통해 API 키와 API 시크릿 전달

            HashMap<String, String> params = new HashMap<>();
            params.put("to", to);    // 수신 전화번호
            params.put("from", fromPhoneNumber);    // 발신 전화번호
            params.put("type", "sms");
            params.put("text", "인증번호는 [" + token + "] 입니다.");

            // 메시지 전송
            coolsms.send(params);
        } catch (Exception e) {
            throw new CoolsmsException("Failed to send SMS", 400);
        }
    }
}