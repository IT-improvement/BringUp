package com.bringup.common.event.Service;

import com.bringup.common.enums.CertificateErrorCode;
import com.bringup.common.event.DTO.request.MailCertificateRequestDto;
import com.bringup.common.event.DTO.response.CertificateMailResponseDto;
import com.bringup.common.event.Entity.EmailVerificationToken;
import com.bringup.common.event.Repository.EmailVerificationTokenRepository;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.company.user.Entity.Company;
import com.bringup.company.user.Repository.CompanyRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final MailService mailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public String createEmailVerificationToken(String email) {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자 생성
        String token = String.valueOf(randomNumber);
        String expiryDate = LocalDateTime.now().plusMinutes(10).format(formatter);  // 토큰 유효 기간 10분

        EmailVerificationToken verificationToken = EmailVerificationToken.builder()
                .token(token)
                .expiryDate(expiryDate)
                .email(email)
                .build();

        emailVerificationTokenRepository.save(verificationToken);
        return token;
    }

    /*public String getCertificateNumber(String phoneNumber) {
        String parsePhoneNum = parsePhoneNumber(phoneNumber); // 전화번호 parse
        String randomNum = RandomNumUtil.createRandomNum(6); // 6자리 임의의 숫자 생성
        log.error("service error 발생");
        coolsmsService.sendCertificationMessage(parsePhoneNum, randomNum);

        return randomNum;
    }

    public void verifyCertificateNumber(VerifyCertificateRequestDto request) {
        // 유효 번호 검증
        if (!validateCertificationNumber(request.getPhoneNumber(), request.certificationNumber()))
            throw new CertificateException(CertificateErrorCode.INVALID_CERTIFCATE_NUMBER);

        redisRepository.deleteCertificationNumber(request.getPhoneNumber());
    }*/

    /*private String parsePhoneNumber(String phoneNumber) {
        String parseNum = phoneNumber.replace("-", "");

        if (!parseNum.matches("[0-9]+")) {
            throw new CertificateException(CertificateErrorCode.INVALID_PHONE_NUMBER);
        }

        return parseNum; // 010-1234-1234 >> 01012341234 형태로 parse
    }*/


    public CertificateMailResponseDto sendEmailCertificateNumber(MailCertificateRequestDto mailCertificateRequestDto) {
        String email = mailCertificateRequestDto.email();

        // 이미 이메일 인증이 진행된 경우 예외 발생
        emailVerificationTokenRepository.findByEmail(email).ifPresent(token -> {
            throw new CertificateException(CertificateErrorCode.EMAIL_ALREADY_VERIFIED);
        });

        // 이메일 인증 토큰 생성 및 발송
        String token = createEmailVerificationToken(email);
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

    public boolean verifyEmail(String token) {
        Optional<EmailVerificationToken> optionalToken = emailVerificationTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return false;
        }

        EmailVerificationToken verificationToken = optionalToken.get();
        if (LocalDateTime.parse(verificationToken.getExpiryDate(), formatter).isBefore(LocalDateTime.now())) {
            return false; // 토큰이 만료됨
        }

        // 인증 성공 시 토큰 삭제 (또는 상태 업데이트)
        emailVerificationTokenRepository.delete(verificationToken);

        return true; // 인증 성공
    }

    public boolean isEmailVerified(String email) {
        // 이메일이 인증되었는지 확인
        return emailVerificationTokenRepository.findByEmail(email).isEmpty();
    }

    public void deleteVerificationTokenByEmail(String email) {
        Optional<EmailVerificationToken> optionalToken = emailVerificationTokenRepository.findByEmail(email);
        optionalToken.ifPresent(emailVerificationTokenRepository::delete);
    }

    public HashMap<String, String> getCertificationMailContent(String certificateNumber) {
        return new HashMap<>() {{
            put("subject", "이메일 인증 코드입니다.");
            put("text", "유효시간은 10분입니다. \n\n" + "인증코드: " + certificateNumber + "\n\n이메일 인증을 완료해 주세요.");
        }};
    }
}
