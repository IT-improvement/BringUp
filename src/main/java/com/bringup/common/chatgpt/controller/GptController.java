package com.bringup.common.chatgpt.controller;

import com.bringup.common.chatgpt.dto.request.PromptRequestDto;
import com.bringup.common.chatgpt.dto.response.PromptResponseDto;
import com.bringup.common.chatgpt.service.OpenAIService;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/openai")
public class GptController {
    private final OpenAIService openAIService;
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/generate/{questionNumber}")
    public ResponseEntity<BfResponse<PromptResponseDto>> generateTextForQuestion(
            @PathVariable int questionNumber,
            @RequestBody PromptRequestDto requestDto) {
        try {
            // OpenAIService를 사용해 해당 질문에 대한 응답 생성
            PromptResponseDto response = openAIService.generateTextForQuestion(questionNumber, requestDto);
            return ResponseEntity.ok(new BfResponse<>(response));
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 질문 번호 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BfResponse<>(new PromptResponseDto("Invalid question number.")));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BfResponse<>(new PromptResponseDto("An error occurred while processing the request.")));
        }
    }
}
