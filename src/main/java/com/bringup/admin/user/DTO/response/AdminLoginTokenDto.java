package com.bringup.admin.user.DTO.response;

import lombok.Builder;

@Builder
public record AdminLoginTokenDto(
        String accessToken
) {
}
