package com.bringup.member.main.controller;

import com.bringup.common.image.ImageService;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.main.dto.*;

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

    /*@PostMapping("/memberInfo")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서비스에서 유저 정보를 가져옴
        MemberInfoDto memberInfoDto = mainService.getMemberInfo(userDetails);
        // 결과 반환
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, memberInfoDto));
    }

    @GetMapping("/premium")
    public ResponseEntity<PremiumAdvertisementDto> getAdvertisements() {
        PremiumAdvertisementDto advertisements = mainService.getSinglePremiumAdvertisement();
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/main")
    public ResponseEntity<List<MainAdvertisementDto>> getActiveCompanyImages() {
        List<MainAdvertisementDto>MainAdvertisementImage= mainService.getMainAdvertisement();
        return ResponseEntity.ok(MainAdvertisementImage);
    }

    @GetMapping("/banner")
    public ResponseEntity<List<BannerAdvertisementDto>> getAd3Advertisements() {
        List<BannerAdvertisementDto> ad3Advertisements = mainService.getBannerAdvertisement();
        return ResponseEntity.ok(ad3Advertisements);
    }
    @GetMapping("/list")
    public ResponseEntity<List<MainRecruitmentDto>> getMainRecruitment() {
        List<MainRecruitmentDto> getMainRecruitment = mainService.getMainRecruitment();
        return ResponseEntity.ok(getMainRecruitment);
    }*/



}