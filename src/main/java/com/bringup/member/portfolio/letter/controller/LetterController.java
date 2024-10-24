package com.bringup.member.portfolio.letter.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.letter.domain.LetterService;
import com.bringup.member.portfolio.letter.dto.response.LetterListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/portfolio/letter")
@RestController
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @GetMapping("/info")
    public ResponseEntity<? super LetterListResponseDto> listLetter(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userIndex = userDetails.getId();
        return letterService.listLetter(userIndex);
    }
}
