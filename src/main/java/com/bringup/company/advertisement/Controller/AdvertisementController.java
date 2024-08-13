package com.bringup.company.advertisement.Controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.company.advertisement.DTO.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.DTO.request.AdvertisementUploadRequestDto;
import com.bringup.company.advertisement.Service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/select")
    public ResponseEntity<BfResponse<?>> selectAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.createAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 선택 완료"));
    }

    @PostMapping("/upload")
    public ResponseEntity<BfResponse<?>> uploadAdvertisementImage(
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "이미지 업로드 성공"));
    }

    @PostMapping("/type")
    public ResponseEntity<BfResponse<?>> selectAdvertisementType(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementType(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 타입 선택 완료"));
    }

    @PostMapping("/display-time")
    public ResponseEntity<BfResponse<?>> selectDisplayTime(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementDisplayTime(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 게시 시간 설정완료"));
    }

    @PostMapping("/extend")
    public ResponseEntity<BfResponse<?>> extendAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.extendAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 게시 시간 연장 완료"));
    }

    @PostMapping("/delete")
    public ResponseEntity<BfResponse<?>> deleteAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.deleteAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 삭제 완료"));
    }

    @PostMapping("/updateImage")
    public ResponseEntity<BfResponse<?>> updateAdvertisementImage(
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "광고 업로드 완료"));
    }
}