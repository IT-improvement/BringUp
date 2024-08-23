package com.bringup.company.advertisement.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.response.AdvertisementResponseDto;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/select")
    public ResponseEntity<BfResponse<?>> selectAdvertisement(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AdvertisementRequestDto requestDto,
            @RequestPart("image") MultipartFile img) {
        advertisementService.createAdvertisement(userDetails, requestDto, img);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 선택 완료"));
    }

    @PostMapping("/upload")
    public ResponseEntity<BfResponse<?>> uploadAdvertisementImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(userDetails, recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "이미지 업로드 성공"));
    }

    @PostMapping("/type")
    public ResponseEntity<BfResponse<?>> selectAdvertisementType(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementType(userDetails, requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 타입 선택 완료"));
    }

    @PostMapping("/display-time")
    public ResponseEntity<BfResponse<?>> selectDisplayTime(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementDisplayTime(userDetails, requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 게시 시간 설정완료"));
    }

    @PostMapping("/extend")
    public ResponseEntity<BfResponse<?>> extendAdvertisement(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.extendAdvertisement(userDetails, requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 게시 시간 연장 완료"));
    }

    @PostMapping("/delete")
    public ResponseEntity<BfResponse<?>> deleteAdvertisement(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.deleteAdvertisement(userDetails, requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 삭제 완료"));
    }

    @PostMapping("/updateImage")
    public ResponseEntity<BfResponse<?>> updateAdvertisementImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(userDetails, recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 업로드 완료"));
    }

    @GetMapping("/list")
    public ResponseEntity<BfResponse<List<AdvertisementResponseDto>>> listAdvertisements(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<AdvertisementResponseDto> advertisements = advertisementService.getAdvertisements(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, advertisements));
    }

    @GetMapping("/detail/{advertisementId}")
    public ResponseEntity<BfResponse<AdvertisementResponseDto>> getAdvertisementDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer advertisementId) {
        AdvertisementResponseDto advertisementDetail = advertisementService.getAdvertisementDetail(userDetails, advertisementId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, advertisementDetail));
    }
}