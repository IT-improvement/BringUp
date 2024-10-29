package com.bringup.common.chatgpt.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PromptRequestDto implements Serializable {
    private String prompt;
}
