package com.bringup.common.event.Controller;

import com.bringup.common.event.DTO.request.MailCertificateRequestDto;
import com.bringup.common.event.DTO.response.CertificateMailResponseDto;
import com.bringup.common.event.Service.CertificateService;
import com.bringup.common.event.exception.CertificateException;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.eclipse.angus.mail.util.MailConnectException;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
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
    private final ErrorResponseHandler errorResponseHandler;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<CertificateMailResponseDto>> sendCertificateMail(
            @Valid @RequestBody MailCertificateRequestDto mailCertificateRequestDto) {
        CertificateMailResponseDto responseDto = certificateService.sendEmailCertificateNumber(mailCertificateRequestDto);

        return ResponseEntity.ok().body(new BfResponse<>(SUCCESS, responseDto));
    }

    /**
     * SMS 인증 코드 전송
     */
    @PostMapping(value = "/sms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<String>> sendSmsCertificate(
            @RequestBody Map<String, String> requestBody) throws CoolsmsException {
        String phoneNumber = requestBody.get("phoneNumber");
        String generatedCode = certificateService.sendSmsCertificateNumber(phoneNumber);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, generatedCode));
    }

    /**
     * 이메일 또는 SMS 인증 코드 검증
     */
    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<?>> verifyCertificate(
            @RequestBody Map<String, String> requestBody) {
        try{
            String certificationNumber = requestBody.get("certificationNumber");

            boolean isValid = certificateService.verifyCertificateNumber(certificationNumber);

            if (isValid) {
                return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("isValid", true)));
            }else{
                return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("isValid", false)));
            }
        }
        catch (CertificateException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
}