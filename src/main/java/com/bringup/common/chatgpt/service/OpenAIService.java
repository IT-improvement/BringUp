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
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final OpenAIConfig openAIConfig;
    private final RestTemplate restTemplate;

    // 메인 메서드: 질문 번호와 사용자 입력을 받아 프롬프트를 생성하고 API 호출
    public PromptResponseDto generateTextForQuestion(int questionNumber, PromptRequestDto dto) {
        String prompt = createPromptForQuestion(questionNumber, dto.getMessages());
        return callOpenAIAPI(prompt);
    }

    // 질문 번호에 따라 프롬프트 생성
    private String createPromptForQuestion(int questionNumber, String userContent) {
        switch (questionNumber) {
            case 1:
                return createPromptForQuestion1(userContent);
            case 2:
                return createPromptForQuestion2(userContent);
            case 3:
                return createPromptForQuestion3(userContent);
            default:
                throw new IllegalArgumentException("유효하지 않은 질문 번호입니다.");
        }
    }

    public PromptResponseDto callOpenAIAPI(String prompt){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(openAIConfig.getMEDIA_TYPE()));
        headers.add(OpenAIConfig.AUTHORIZATION, OpenAIConfig.BEARER + openAIConfig.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAIConfig.getModel());
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));
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
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }

    // 1번 질문에 대한 프롬프트 생성
    private String createPromptForQuestion1(String userContent) {
        return "다음은 자소서 질문에 대한 사용자의 응답입니다. " +
                "질문: '학교 생활이나 사회경험 중 가장 어려웠거나 힘들었던 경험은 무엇이며, " +
                "그 문제를 해결하기 위해 노력한 점이나 성공적으로 변화를 이루었던 경험을 작성해 주십시오.' " +
                "사용자의 답변을 더 풍부하고 논리적이며 설득력 있게 보완해 주세요. " +
                "비속어는 적절한 대체어로 수정해주세요. " +
                "800자 이내로 서술해 주세요. " +
                "응답은 한글로 작성해 주세요.\n\n" +
                "사용자 응답: \"" + userContent + "\"\n\n" +
                "개선된 응답:";
    }

    // 2번 질문에 대한 프롬프트 생성
    private String createPromptForQuestion2(String userContent) {
        return "The following is a user's answer to a self-introduction question for a job application. " +
                "The question is: 'What word or phrase best describes your personal values, and what " +
                "experiences or individuals have most influenced these values?' " +
                "Make the answer more compelling and insightful.\n\n" +
                "User's answer: \"" + userContent + "\"\n\n" +
                "Please rewrite the answer, providing a clearer and stronger explanation.";
    }

    // 3번 질문에 대한 프롬프트 생성
    private String createPromptForQuestion3(String userContent) {
        return "The following is a user's answer to a self-introduction question for a job application. " +
                "The question is: 'Describe your skills, knowledge, experience, and passion related to the field " +
                "you are applying for. Mention any relevant projects, competitions, or research work.' " +
                "Make the answer more compelling and persuasive.\n\n" +
                "User's answer: \"" + userContent + "\"\n\n" +
                "Please rewrite the answer to better highlight your qualifications and enthusiasm.";
    }
}