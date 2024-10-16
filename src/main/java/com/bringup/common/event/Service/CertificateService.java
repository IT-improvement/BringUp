package com.bringup.common.event.Service;

import com.bringup.common.enums.CertificateErrorCode;
import com.bringup.common.event.DTO.request.MailCertificateRequestDto;
import com.bringup.common.event.DTO.response.CertificateMailResponseDto;
import com.bringup.common.event.Entity.EmailVerificationToken;
import com.bringup.common.event.Repository.EmailVerificationTokenRepository;
import com.bringup.common.event.exception.CertificateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import static com.bringup.common.enums.CertificateErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final MailService mailService;
    private final CoolsmsService coolsmsService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // 이메일 또는 전화번호에 대한 인증 토큰 생성
    public String createVerificationToken(String identifier) {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자 생성
        String token = String.valueOf(randomNumber);
        String expiryDate = LocalDateTime.now().plusMinutes(10).format(formatter);  // 토큰 유효 기간 10분

        EmailVerificationToken verificationToken = EmailVerificationToken.builder()
                .token(token)
                .expiryDate(expiryDate)
                .email(identifier)  // email 필드에 이메일 또는 전화번호를 저장
                .build();

        emailVerificationTokenRepository.save(verificationToken);
        return token;
    }

    // 이메일 인증 코드 생성 및 전송
    public CertificateMailResponseDto sendEmailCertificateNumber(MailCertificateRequestDto mailCertificateRequestDto) {
        String email = mailCertificateRequestDto.email();

        // 이미 인증이 진행된 경우 예외 발생
        emailVerificationTokenRepository.findByEmail(email).ifPresent(token -> {
            throw new CertificateException(EMAIL_ALREADY_VERIFIED);
        });

        // 인증 토큰 생성 및 발송
        String token = createVerificationToken(email);
        HashMap<String, String> content = getCertificationMailContent(token);

        try {
            mailService.sendMail(email, content);
        } catch (MessagingException e) {
            throw new CertificateException(CertificateErrorCode.MAIL_SEND_ERROR);
        }

        return CertificateMailResponseDto.builder()
                .certificationNumber(token)
                .mailExpirationSeconds(600) // 10분(초 단위)
                .build();
    }

    // SMS 인증 코드 생성 및 전송
    public String sendSmsCertificateNumber(String phoneNumber) throws CoolsmsException {
        String generatedCode = createVerificationToken(phoneNumber); // 전화번호로 인증 토큰 생성
        coolsmsService.sendSms(phoneNumber, generatedCode); // SMS 전송 로직 호출
        return generatedCode;
    }

    // 인증 코드 검증 (이메일 또는 전화번호)
    public boolean verifyCertificateNumber(String identifier, String token) {
        Optional<EmailVerificationToken> optionalToken = emailVerificationTokenRepository.findByEmail(identifier);

        if (optionalToken.isEmpty()) {
            throw new CertificateException(INVALID_CERTIFCATE_NUMBER);
        }

        EmailVerificationToken verificationToken = optionalToken.get();

        // 토큰 만료 확인
        if (LocalDateTime.parse(verificationToken.getExpiryDate(), formatter).isBefore(LocalDateTime.now())) {
            throw new CertificateException(TO_LATE_VERIFIED);
        }

        // 토큰 검증
        if (!verificationToken.getToken().equals(token)) {
            throw new CertificateException(INVALID_CERTIFCATE_NUMBER);
        }

        // 인증 성공 시 토큰 삭제 또는 상태 업데이트
        emailVerificationTokenRepository.delete(verificationToken);

        return true; // 인증 성공
    }

    // 이메일 또는 전화번호가 인증되었는지 확인
    public boolean isVerified(String identifier) {
        return emailVerificationTokenRepository.findByEmail(identifier).isEmpty();
    }

    // 인증 토큰 삭제
    public void deleteVerificationTokenByIdentifier(String identifier) {
        Optional<EmailVerificationToken> optionalToken = emailVerificationTokenRepository.findByEmail(identifier);
        optionalToken.ifPresent(emailVerificationTokenRepository::delete);
    }

    // 인증 메일 내용 생성
    public HashMap<String, String> getCertificationMailContent(String certificateNumber) {
        return new HashMap<>() {{
            put("subject", "이메일 인증 코드입니다.");
            put("text", "유효시간은 10분입니다. \n\n" + "인증코드: " + certificateNumber + "\n\n이메일 인증을 완료해 주세요.");
        }};
    }
}
