package com.bringup.member.portfolio.letter.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.letter.domain.LetterService;
import com.bringup.member.portfolio.letter.dto.request.LetterInsertRequestDto;
import com.bringup.member.portfolio.letter.dto.response.LetterListResponseDto;
import com.bringup.member.portfolio.letter.dto.response.LetterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public ResponseEntity<? super LetterResponseDto> insertLetter(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LetterInsertRequestDto letterInsertRequestDto) {
        int userIndex = userDetails.getId();
        return letterService.insertLetter(userIndex, letterInsertRequestDto);
    }
}
