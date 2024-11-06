package com.bringup.member.portfolio.award.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.award.domain.AwardService;
import com.bringup.member.portfolio.award.dto.AwardListResponseDto;
import com.bringup.member.portfolio.award.dto.AwardRequestDto;
import com.bringup.member.portfolio.award.dto.AwardResponseDto;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/award")
public class AwardController {

    private final AwardService awardService;

    @PostMapping("/insert")
    public ResponseEntity<? super AwardResponseDto> insertAward(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AwardRequestDto awardRequestDto) {
        int userCode = userDetails.getId();
        return awardService.insertAward(userCode, awardRequestDto);
    }

    @GetMapping("/list")
    public ResponseEntity<? super AwardListResponseDto> getAwardList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userCode = userDetails.getId();
        return awardService.getListAward(userCode);
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<? super AwardResponseDto> deleteAward(@PathVariable String index){
        int id = Integer.parseInt(index);
        return awardService.deleteAward(id);
    }
}
