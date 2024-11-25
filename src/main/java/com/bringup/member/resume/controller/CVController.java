package com.bringup.member.resume.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.request.CVPortfolioRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import com.bringup.member.resume.dto.response.CVListResponseDto;
import com.bringup.member.resume.dto.response.CVReadResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cv")
public class CVController {

    private final CVService cvService;

    @PostMapping("/insert")
    public ResponseEntity<? super CVInsertResponseDto> insertCv(@RequestBody @Valid CVInsertRequestDto requestBody, @AuthenticationPrincipal UserDetailsImpl user) {
        int code = user.getId();
        ResponseEntity<? super CVInsertResponseDto> response = cvService.insertCv(requestBody, code);
        return response;
    }

    @GetMapping("/{cvIndex}")
    public ResponseEntity<? super CVReadResponseDto> readCv(@PathVariable("cvIndex")String index){
        return cvService.readCV(index);
    }

    @GetMapping("/list")
    public ResponseEntity<? super CVListResponseDto> readCvList(@AuthenticationPrincipal UserDetailsImpl user){
        int code = user.getId();
        return cvService.listCv(code);
    }
}

