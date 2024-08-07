package com.bringup.company.user.DTO.response;

import lombok.Builder;

@Builder
public record LoginTokenDto(
        String accessToken
) {
}
