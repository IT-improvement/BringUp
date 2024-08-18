package com.bringup.company.advertisement.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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