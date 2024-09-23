package com.bringup.company.advertisement.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.response.AdvertisementResponseDto;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.service.AdvertisementService;
import com.bringup.company.recruitment.dto.response.RecruitmentDetailResponseDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
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
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/upload")
    public ResponseEntity<BfResponse<?>> uploadAd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @RequestBody AdvertisementRequestDto advertisementRequestDto,
                                                  @RequestPart("image") MultipartFile img) {
        try {
            advertisementService.createAdvertisement(userDetails, advertisementRequestDto, img);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "업로드 성공"));
        } catch (AdvertisementException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<BfResponse<?>> listAd(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            List<AdvertisementResponseDto> advertisements = advertisementService.getAdvertisements(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, advertisements));
        } catch (RecruitmentException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{ad_index}") // 광고 디테일 확인
    public ResponseEntity<BfResponse<?>> detailAd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("ad_index") int ad_index) {
        try {
            AdvertisementResponseDto ad_detail = advertisementService.getAdvertisementDetail(userDetails, ad_index);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, ad_detail));
        } catch (AdvertisementException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{ad_index}")
    public ResponseEntity<BfResponse<?>> delAd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @PathVariable("ad_index") int ad_index,
                                               @RequestBody String reason) {
        try {
            advertisementService.deleteAdvertisement(userDetails, ad_index, reason);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "삭제완료"));
        } catch (AdvertisementException e) {
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        } catch (Exception e) {
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ad_index}")
    public ResponseEntity<BfResponse<?>> updateAd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable("ad_index") int ad_index,
                                                  @RequestPart("image") MultipartFile image,
                                                  @RequestBody Integer date){
        try {
            advertisementService.updateAdvertisement(userDetails, ad_index, image, date);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "업데이트 완료"));
        } catch (AdvertisementException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }

    }
}