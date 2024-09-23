package com.bringup.common.bookmark.controller;

import com.bringup.common.bookmark.domain.service.CompanyBookMarkService;
import com.bringup.common.bookmark.dto.request.CompanyBookMarkRequestDto;
import com.bringup.common.bookmark.dto.response.CandidateResponseDto;
import com.bringup.common.bookmark.dto.response.CompanyBookMarkResponseDto;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
public class CompanyBookMarkContoller {
    @Autowired
    private CompanyBookMarkService companyBookMarkService;

    @GetMapping("/mem/addCompany")
    public ResponseEntity<BfResponse<?>> addCompany(@AuthenticationPrincipal UserDetailsImpl userDetails){
        companyBookMarkService.addCompany(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "기업 저장 완료"));
    }

    @GetMapping("/mem/addCompany/list")
    public ResponseEntity<List<CompanyBookMarkResponseDto>> getCompanyBookMarkList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<CompanyBookMarkResponseDto> companyBookMarkResponseDtoList = companyBookMarkService.companyBookMarkList(userDetails);
        return ResponseEntity.ok(companyBookMarkResponseDtoList);
    }

    @GetMapping("/com/headhunt/candidate/{cv_index}")
    public ResponseEntity<BfResponse<?>> addcandidate(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int cv_index){
        companyBookMarkService.addCandidate(userDetails, cv_index);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "후보자 저장 완료"));
    }

    @GetMapping("/com/headhunt/candidate/list")
    public ResponseEntity<List<CandidateResponseDto>> getCandidateList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CandidateResponseDto> candidateList = companyBookMarkService.candidateList(userDetails);
        return ResponseEntity.ok(candidateList);
    }


}
