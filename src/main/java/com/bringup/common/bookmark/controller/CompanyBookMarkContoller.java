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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
public class CompanyBookMarkContoller {
    @Autowired
    private CompanyBookMarkService companyBookMarkService;

    @PostMapping("/mem/addCompany/{company_index}")
    public ResponseEntity<BfResponse<?>> addCompany(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("company_index") int companyIndex){
        companyBookMarkService.addCompany(userDetails, companyIndex);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "기업 저장 완료"));
    }

    @GetMapping("/mem/addCompany/list")
    public ResponseEntity<List<CompanyBookMarkResponseDto>> getCompanyBookMarkList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<CompanyBookMarkResponseDto> companyBookMarkResponseDtoList = companyBookMarkService.companyBookMarkList(userDetails);
        return ResponseEntity.ok(companyBookMarkResponseDtoList);
    }

    @GetMapping("/mem/delCompany/{companyIndex}")
    public ResponseEntity<BfResponse<?>> delCompany(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int companyIndex){
        companyBookMarkService.delCompany(userDetails, companyIndex);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "기업 삭제 완료"));
    }

    @PostMapping("/com/headhunt/candidate/save/{cv_index}")
    public ResponseEntity<BfResponse<?>> addcandidate(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int cv_index){
        companyBookMarkService.addCandidate(userDetails, cv_index);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "후보자 저장 완료"));
    }

    @DeleteMapping("/com/headhunt/candidate/del/{cv_index}")
    public ResponseEntity<BfResponse<?>> delcandidate(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int cv_index){
        companyBookMarkService.delCandidate(userDetails, cv_index);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "후보자 삭제 완료"));
    }

    @GetMapping("/com/headhunt/candidate/list")
    public ResponseEntity<BfResponse<?>> getCandidateList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CandidateResponseDto> candidateList = companyBookMarkService.candidateList(userDetails);
        return ResponseEntity.ok(new BfResponse<>(candidateList));
    }

    @PostMapping("/com/headhunt/candidate/check-saved")
    public ResponseEntity<BfResponse<?>> checkCandidateSaved(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Map<String, List<Integer>> requestBody) {

        List<Integer> cvIndices = requestBody.get("cvIndices");
        Map<Integer, Boolean> savedStatusMap = companyBookMarkService.checkCandidatesSaved(userDetails, cvIndices);

        return ResponseEntity.ok(new BfResponse<>(savedStatusMap));
    }

    @GetMapping("/com/bookmarkCount")
    public ResponseEntity<BfResponse<?>> countBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails){
        int count = companyBookMarkService.countBookmark(userDetails);

        return ResponseEntity.ok(new BfResponse<>(count));
    }
}
