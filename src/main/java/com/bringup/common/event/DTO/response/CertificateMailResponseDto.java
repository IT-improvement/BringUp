package com.bringup.common.event.DTO.response;

import lombok.Builder;

@Builder
public record CertificateMailResponseDto(
        int mailExpirationSeconds,

        String certificationNumber
) {
}
