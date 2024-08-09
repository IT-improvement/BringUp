package com.bringup.common.event.Controller;

import com.bringup.common.event.DTO.request.MailCertificateRequestDto;
import com.bringup.common.event.DTO.response.CertificateMailResponseDto;
import com.bringup.common.event.Service.CertificateService;
import com.bringup.common.response.BfResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class CertificateController {
    private final CertificateService certificationService;

    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<CertificateMailResponseDto>> sendCertificateMail(
            @Valid @RequestBody MailCertificateRequestDto mailCertificateRequestDto
    ) {
        CertificateMailResponseDto responseDto = certificationService.sendEmailCertificateNumber(
                mailCertificateRequestDto);

        return ResponseEntity.ok()
                .body(new BfResponse<>(SUCCESS, responseDto));
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BfResponse<?>> verifyMailCertificationNumber(
            @RequestParam String email, @RequestParam String certificationNumber) {
        boolean isValid = certificationService.verifyCertificateEmailNumber(email, certificationNumber);
        return ResponseEntity.ok()
                .body(new BfResponse<>(SUCCESS, Map.of("isValid", isValid)));
    }
}