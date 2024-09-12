package com.bringup.member.main.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.main.dto.UserAdvertisementResponseDto;
import com.bringup.member.main.dto.MemberInfoDto;
import com.bringup.member.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {


    private final MainService mainService;

    @PostMapping("/memberInfo")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서비스에서 유저 정보를 가져옴
        MemberInfoDto memberInfoDto = mainService.getMemberInfo(userDetails);
        // 결과 반환
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, memberInfoDto));
    }


    @GetMapping("/advertisement")
    public ResponseEntity<List<UserAdvertisementResponseDto>> getAllAdvertisements(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 광고 데이터를 가져와서 클라이언트로 반환
        List<UserAdvertisementResponseDto> advertisements = mainService.getAdvertisements(userDetails);
        return ResponseEntity.ok(advertisements);
    }

}
