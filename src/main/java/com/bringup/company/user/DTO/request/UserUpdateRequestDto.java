package com.bringup.company.user.DTO.request;

public record UserUpdateRequestDto(
        String name,                            // 닉네임

        String email
) {
}
