package com.bringup.company.advertisement.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.PremiumAdRequestDto;
import com.bringup.company.advertisement.dto.response.AdvertisementResponseDto;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.TimeSlot;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.service.*;
import com.bringup.company.recruitment.dto.response.RecruitmentDetailResponseDto;
import com.bringup.company.recruitment.dto.response.RecruitmentResponseDto;
import com.bringup.company.recruitment.exception.RecruitmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final PremiumAdService premiumAdService;
    private final BannerAdService bannerAdService;
    private final AnnouncementAdService announcementAdService;
    private final MainAdService mainAdService;
    private final ErrorResponseHandler errorResponseHandler;

    /**
     * 프리미엄 가능한 시간 파악하는 컨트롤러
     * @param date
     * @return
     */
    @GetMapping("/premium/available-times/{date}")
    public ResponseEntity<BfResponse<?>> getAvailableTimes(@PathVariable LocalDate date) {
        try{
            List<TimeSlot> availableTimes = premiumAdService.getAvailableTimes(date);
            return ResponseEntity.ok(new BfResponse<>(availableTimes));
        } catch (AdvertisementException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    /**
     *
     * @param premiumAdDto
     * @param img
     * @return
     */
    @PostMapping("/premium")
    public ResponseEntity<BfResponse<?>> createPremiumAd(@RequestBody PremiumAdRequestDto premiumAdDto,
                                                         @RequestPart("image") MultipartFile img) {
        try{
            premiumAdService.createPremiumAd(premiumAdDto, img);
            BfResponse<String> response = new BfResponse<>(SUCCESS, "Premium Advertisement Created Successfully");
            return ResponseEntity.ok(response);
        } catch (AdvertisementException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/premium/{premiumAdId}")
    public ResponseEntity<BfResponse<String>> updatePremiumAd(
            @PathVariable int premiumAdId,
            @RequestBody PremiumAdRequestDto premiumAdDto,
            @RequestParam("image") MultipartFile img) {

        premiumAdService.updatePremiumAd(premiumAdId, premiumAdDto, img);
        BfResponse<String> response = new BfResponse<>(SUCCESS,"Premium advertisement updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/premium/{premiumAdId}")
    public ResponseEntity<BfResponse<String>> deletePremiumAd(@PathVariable int premiumAdId) {
        premiumAdService.deletePremiumAd(premiumAdId);
        BfResponse<String> response = new BfResponse<>(SUCCESS, "Premium advertisement deleted successfully");
        return ResponseEntity.ok(response);
    }


    /**
     * 캘린더 비활성화용
     * @return
 */
    /*@GetMapping("/premium/sold-out-dates")
    public ResponseEntity<BfResponse<?>> getSoldOutDates() {
        List<LocalDate> soldOutDates = premiumAdService.getSoldOutDates();
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, soldOutDates));
    }*/



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