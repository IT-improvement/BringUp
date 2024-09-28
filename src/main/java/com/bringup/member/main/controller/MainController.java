package com.bringup.member.main.controller;

import com.bringup.common.image.ImageService;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.main.dto.CompanyImageDto;
import com.bringup.member.main.dto.MainRecruitmentDto;
import com.bringup.member.main.dto.PremiumAdvertisementDto;
import com.bringup.member.main.dto.MemberInfoDto;

import com.bringup.member.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final ImageService imageService;

    @PostMapping("/memberInfo")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서비스에서 유저 정보를 가져옴
        MemberInfoDto memberInfoDto = mainService.getMemberInfo(userDetails);
        // 결과 반환
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, memberInfoDto));
    }

    @GetMapping("/premium")
    public ResponseEntity<List<PremiumAdvertisementDto>> getAdvertisements() {
        // 서비스에서 광고 데이터를 받아 응답으로 반환
        List<PremiumAdvertisementDto> advertisements = mainService.getPremiumAdvertisement();
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/recruitmentImage")
    public ResponseEntity<List<CompanyImageDto>> getActiveCompanyImages() {
        List<CompanyImageDto> companyImages = mainService.getActiveCompanyImages();
        return ResponseEntity.ok(companyImages);
    }

  /*  @GetMapping("/ad3Advertisements")
    public ResponseEntity<List<PremiumAdvertisementDto>> getAd3Advertisements() {
        List<PremiumAdvertisementDto> ad3Advertisements = mainService.getAd3Advertisements();
        return ResponseEntity.ok(ad3Advertisements);
    }*/

    @GetMapping("/recruitment")
    public ResponseEntity<List<MainRecruitmentDto>> getMainRecruitment() {
        List<MainRecruitmentDto> getMainRecruitment = mainService.getMainRecruitment();
        return ResponseEntity.ok(getMainRecruitment);
    }



}