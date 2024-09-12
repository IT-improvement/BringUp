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

    /*@PostMapping("/company_bookmark/add")
    public ResponseEntity<CompanyBookMarkResponseDto> addCompanyBookMark(@RequestBody CompanyBookMarkRequestDto companyBookMarkRequestDto){
        CompanyBookMarkResponseDto companyBookMarkResponseDto = companyBookMarkService.addCompanyBookMark(companyBookMarkRequestDto);
        return ResponseEntity.ok(companyBookMarkResponseDto);
    }

    @GetMapping("/company_bookmark/{userIndex}")
    public ResponseEntity<List<CompanyBookMarkResponseDto>> getCompanyBookMarks(@PathVariable int userIndex){
        List<CompanyBookMarkResponseDto> companyBookMarkEntityList = companyBookMarkService.getCompanyBookMarks(userIndex);
        return ResponseEntity.ok(companyBookMarkEntityList);
    }

    @PutMapping("/company_bookmark/delete")
    public ResponseEntity<Void> removeCompanyBookMark(@RequestBody CompanyBookMarkRequestDto companyBookMarkRequestDto){
        companyBookMarkService.removeCompanyBookMark(companyBookMarkRequestDto);
        return ResponseEntity.ok().build();
    }*/

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
