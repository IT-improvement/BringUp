package com.bringup.common.chatgpt.service;

import com.bringup.common.chatgpt.dto.request.PromptRequestDto;
import com.bringup.common.chatgpt.config.OpenAIConfig;
import com.bringup.common.chatgpt.dto.request.ChatGptRequestDto;
import com.bringup.common.chatgpt.dto.response.PromptResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final OpenAIConfig openAIConfig;
    private final RestTemplate restTemplate;

    public PromptResponseDto generateText(PromptRequestDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "Bearer " + openAIConfig.getApiKey());

        // 요청 본문 생성 (prompt, maxTokens, temperature 포함)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAIConfig.getModel());
        requestBody.put("prompt", dto.getPrompt());
        requestBody.put("max_tokens", openAIConfig.getMaxTokens());
        requestBody.put("temperature", openAIConfig.getTemperature());

        // 요청 엔터티 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // API 호출 및 응답 처리
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                openAIConfig.getApiUrl(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // OpenAI의 응답에서 텍스트 추출
        String generatedText = extractTextFromResponse(responseEntity.getBody());
        return new PromptResponseDto(generatedText);
    }

    private String extractTextFromResponse(String responseBody) {
        // JSON 파싱을 통해 응답에서 텍스트 추출
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            return root.path("choices").get(0).path("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}