package com.bringup.member.resume.controller;

import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.request.CVPortfolioRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import com.bringup.member.resume.dto.response.CVReadResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/cv")
    public String listCv(@AuthenticationPrincipal String userIndex){
        return "member/resume/list";
    }

    @GetMapping("/cv/{cvindex}")
    public String readCv(@PathVariable(name = "cvindex") String cvindex, Model model){
        CVReadResponseDto response = cvService.readCv(cvindex);
        //model.addAttribute("cvImage",response.getCvImage());
        model.addAttribute("mainCv",response.isMainCv());
        //model.addAttribute("education",response.getEducation());
        model.addAttribute("skill",response.getSkill());
        model.addAttribute("userIndex",response.getUserIndex());
        return "member/resume/cv";
    }
}
