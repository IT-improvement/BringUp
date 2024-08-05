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
@RequestMapping("/company/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/select")
    public ResponseEntity<BfResponse<?>> selectAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.createAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Advertisement selected successfully"));
    }

    @PostMapping("/upload")
    public ResponseEntity<BfResponse<?>> uploadAdvertisementImage(
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Image uploaded successfully"));
    }

    @PostMapping("/type")
    public ResponseEntity<BfResponse<?>> selectAdvertisementType(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementType(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Advertisement type selected successfully"));
    }

    @PostMapping("/display-time")
    public ResponseEntity<BfResponse<?>> selectDisplayTime(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.updateAdvertisementDisplayTime(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Display time set successfully"));
    }

    @PostMapping("/extend")
    public ResponseEntity<BfResponse<?>> extendAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.extendAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Advertisement extension requested successfully"));
    }

    @PostMapping("/delete")
    public ResponseEntity<BfResponse<?>> deleteAdvertisement(@RequestBody AdvertisementRequestDto requestDto) {
        advertisementService.deleteAdvertisement(requestDto);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Advertisement deleted successfully"));
    }

    @PostMapping("/updateImage")
    public ResponseEntity<BfResponse<?>> updateAdvertisementImage(
            @RequestParam("recruitmentIndex") int recruitmentIndex,
            @RequestPart("image") MultipartFile image) {
        advertisementService.uploadAdvertisementImage(recruitmentIndex, image);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "Advertisement updated successfully"));
    }
}