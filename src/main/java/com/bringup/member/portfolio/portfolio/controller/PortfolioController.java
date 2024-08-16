package com.bringup.member.portfolio.portfolio.controller;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.portfolio.domain.PortfolioService;
import com.bringup.member.portfolio.portfolio.dto.PortfolioInsertResponseDto;
import com.bringup.member.portfolio.portfolio.dto.PortfolioReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/list")
    public ResponseEntity<? super PortfolioReponseDto> getList(@AuthenticationPrincipal String userCode){
        ResponseEntity<? super PortfolioReponseDto> respone = portfolioService.portfolioList(userCode);
        return respone;
    }

    @PostMapping("/github")
    public ResponseEntity<? super PortfolioInsertResponseDto> insertGithub(@AuthenticationPrincipal String userCode){

        return PortfolioInsertResponseDto.success();
    }
}
