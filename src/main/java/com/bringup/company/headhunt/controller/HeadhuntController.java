/*
package com.bringup.company.headhunt.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.headhunt.dto.response.HeadhuntResponseDto;
import com.bringup.company.headhunt.service.HeadhuntService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com/headhunt")
public class HeadhuntController {

    private final HeadhuntService headHuntService;

    @GetMapping("/recommend")
    public ResponseEntity<BfResponse<List<HeadhuntResponseDto>>> recommendCVs(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<HeadhuntResponseDto> recommendations = headHuntService.recommendMembershipCVsBasedOnCompanySkills(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, recommendations));
    }

    @GetMapping("/list")
    public ResponseEntity<BfResponse<List<HeadhuntResponseDto>>> listAllCVs() {
        List<HeadhuntResponseDto> allCVs = headHuntService.listAllCVs();
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, allCVs));
    }

    @GetMapping("/list/saved")
    public ResponseEntity<BfResponse<List<HeadhuntResponseDto>>> listSavedCVs(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<HeadhuntResponseDto> savedCVs = headHuntService.listSavedCVs(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, savedCVs));
    }
}
*/
