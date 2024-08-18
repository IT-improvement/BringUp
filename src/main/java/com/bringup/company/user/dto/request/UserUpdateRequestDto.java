package com.bringup.company.user.dto.request;

public record UserUpdateRequestDto(
        String name,                            // 닉네임

        String email
) {
}
