package com.bringup.common.chatgpt.dto.response;

import com.bringup.common.chatgpt.config.Choice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 추가
@Builder
public class PromptResponseDto implements Serializable {
    private String response;
}
