package com.bringup.company.member.DTO.request;

public record UserUpdateRequestDto(
        String name,                            // 닉네임

        String email
) {
}
