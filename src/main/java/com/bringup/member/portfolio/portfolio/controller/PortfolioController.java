package com.bringup.member.portfolio.portfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @GetMapping("/list")
    public ResponseEntity<?> getList(@AuthenticationPrincipal String userCode){

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
