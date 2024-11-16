package com.bringup.member.portfolio.certificate.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.certificate.domain.CertificateService;
import com.bringup.member.portfolio.certificate.dto.CertificateListResponseDto;
import com.bringup.member.portfolio.certificate.dto.CertificateRequestDto;
import com.bringup.member.portfolio.certificate.dto.CertificateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mem/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/list")
    public ResponseEntity<? super CertificateListResponseDto> getList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int id = userDetails.getId();
        return certificateService.getList(id);
    }

    @PostMapping("/insert")
    public ResponseEntity<? super CertificateResponseDto> insertCertificate(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CertificateRequestDto certificateRequestDto) {
        int id = userDetails.getId();
        return certificateService.insertCertificate(id, certificateRequestDto);
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<? super CertificateResponseDto> deleteCertificate(@PathVariable String index) {
        return certificateService.deleteCertificate(index);
    }
}
