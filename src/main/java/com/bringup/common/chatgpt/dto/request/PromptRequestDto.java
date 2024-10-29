package com.bringup.common.chatgpt.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PromptRequestDto{
    private String messages;
}
