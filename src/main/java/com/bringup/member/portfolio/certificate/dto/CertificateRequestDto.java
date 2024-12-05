package com.bringup.member.portfolio.certificate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateRequestDto {
    private String title;
    private String issueStatus;
    private String issueCenter;
    private String certificateType;
}
