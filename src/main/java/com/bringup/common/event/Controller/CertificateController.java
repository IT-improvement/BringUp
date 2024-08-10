package com.bringup.common.event.Controller;

import com.bringup.common.event.DTO.request.MailCertificateRequestDto;
import com.bringup.common.event.DTO.response.CertificateMailResponseDto;
import com.bringup.common.event.Service.CertificateService;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.common.response.BfResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.util.MailConnectException;
import org.hibernate.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bringup.common.enums.CertificateErrorCode.INVALID_CERTIFCATE_NUMBER;
import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class CertificateController {
    private final CertificateService certificateService;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<CertificateMailResponseDto>> sendCertificateMail(
            @Valid @RequestBody MailCertificateRequestDto mailCertificateRequestDto
    ) {
        CertificateMailResponseDto responseDto = certificateService.sendEmailCertificateNumber(
                mailCertificateRequestDto);

        return ResponseEntity.ok()
                .body(new BfResponse<>(SUCCESS, responseDto));
    }

    /**
     * 이메일 인증 코드 검증
     */
    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<?>> verifyMailCertificationNumber(
            @RequestParam String certificationNumber) {
        boolean isValid = certificateService.verifyEmail(certificationNumber);

        if (isValid) {
            return ResponseEntity.ok()
                    .body(new BfResponse<>(SUCCESS, Map.of("isValid", true)));
        }else{
            return ResponseEntity.ok()
                    .body(new BfResponse<>(new CertificateException(INVALID_CERTIFCATE_NUMBER)));
        }
    }
}