package com.bringup.member.board.dto.response;

import lombok.Getter;

@Getter
public class SuccessResponseDto {
    private boolean success;

    public SuccessResponseDto(boolean success){
        this.success = success;
    }
}
