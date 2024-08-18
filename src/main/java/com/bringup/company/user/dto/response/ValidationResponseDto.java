package com.bringup.company.user.dto.response;

import java.util.List;

public record ValidationResponseDto(
        int requestCnt,
        String statusCode,
        List<ValidationResult> data
) {
    public record ValidationResult(
            String bNo,
            String valid,
            String validMsg
    ) {
    }
}