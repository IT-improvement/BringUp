package com.bringup.company.member.DTO.response;

import lombok.Builder;

@Builder
public record LoginTokenDto(
        String accessToken
) {
}
