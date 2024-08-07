package com.bringup.member.resume.controller;

import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.request.CVPortfolioRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @PostMapping("/cv/insert")
    public ResponseEntity<? super CVInsertResponseDto> insertCv(@RequestBody @Valid CVInsertRequestDto requestBody){
        ResponseEntity<? super CVInsertResponseDto> response = cvService.insertCv(requestBody);
        return response;
    }

    @PostMapping("/cv/portfolio")
    public ResponseEntity<? super CVInsertResponseDto> insertPortfolio(@RequestParam @Valid CVPortfolioRequestDto requestBody){
        ResponseEntity<? super CVInsertResponseDto> response = cvService.insertPortfolio(requestBody);
        return response;
    }
}
