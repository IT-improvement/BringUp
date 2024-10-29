package com.bringup.common.chatgpt.controller;

import com.bringup.common.chatgpt.dto.request.PromptRequestDto;
import com.bringup.common.chatgpt.dto.response.PromptResponseDto;
import com.bringup.common.chatgpt.service.OpenAIService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/openai")
public class GptController {
    private final OpenAIService openAIService;

    @PostMapping("/generate")
    public PromptResponseDto generateText(@RequestBody PromptRequestDto requestDto) {
        return openAIService.generateText(requestDto);
    }
}
