package com.bringup.company.user.dto.response;

import lombok.Builder;

@Builder
public record LoginTokenDto(
        String accessToken
) {
}
