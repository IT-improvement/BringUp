package com.bringup.company.advertisement.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.*;
import com.bringup.company.advertisement.dto.response.*;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
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

    private final PremiumAdService premiumAdService;
    private final BannerAdService bannerAdService;
    private final AnnouncementAdService announcementAdService;
    private final MainAdService mainAdService;
    private final ErrorResponseHandler errorResponseHandler;


    //-------------프리미엄 라인----------------------------------------------
    /**
     * 프리미엄 가능한 시간 파악하는 컨트롤러
     * @return
     */
    @PostMapping("/premium/available-times")
    public ResponseEntity<BfResponse<?>> getAvailableTimes(
            @RequestBody PremiumAdRequestDto premiumAdDto) {
        try{
            AvailableDatesResponseDto availableDates = premiumAdService.getAvailableTimeSlotsAndDiscount(premiumAdDto);
            return ResponseEntity.ok(new BfResponse<>(availableDates));
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
                                                         @RequestPart("image") MultipartFile img,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            premiumAdService.createPremiumAd(premiumAdDto, img, userDetails);
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
            @RequestPart("image") MultipartFile img,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        premiumAdService.updatePremiumAd(premiumAdId, premiumAdDto, img, userDetails);
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
     * TODO : 주의점! 디테일 잡는부분은 광고index임 실수로 프리미엄 index 안넣게 주의
     * @param ad_index
     * @param userDetails
     */
    @GetMapping("/premium/detail/{ad_index}")
    public ResponseEntity<BfResponse<?>> detailPremiumAd(
            @PathVariable int ad_index,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        PremiumAdResponseDto pad = premiumAdService.getPremiumAdDetail(userDetails, ad_index);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, pad));
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

    //-------------메인 라인----------------------------------------------

    /*@GetMapping("/main/available-times/{date}")
    public ResponseEntity<BfResponse<?>> getMainAvailableTimes(@PathVariable LocalDate date) {
        try{
            List<TimeSlot> availableTimes = mainAdService.getAvailableTimes(date);
            return ResponseEntity.ok(new BfResponse<>(availableTimes));
        } catch (AdvertisementException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }
*/
    /**
     *
     * @param mainAdDto
     * @param img
     * @return
     */
    @PostMapping("/main")
    public ResponseEntity<BfResponse<?>> createMainAd(@RequestBody MainAdRequestDto mainAdDto,
                                                         @RequestPart("image") MultipartFile img,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            mainAdService.createMainAd(mainAdDto, img, userDetails);
            BfResponse<String> response = new BfResponse<>(SUCCESS, "메인광고 생성완료");
            return ResponseEntity.ok(response);
        } catch (AdvertisementException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/main/{mainAdId}")
    public ResponseEntity<BfResponse<String>> updateMainAd(
            @PathVariable int mainAdId,
            @RequestBody MainAdRequestDto mainAdDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        mainAdService.updateMainAd(mainAdId, mainAdDto, userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "메인광고 수정 완료"));
    }

    @DeleteMapping("/main/{mainAdId}")
    public ResponseEntity<BfResponse<String>> deleteMainAd(@PathVariable int mainAdId) {
        mainAdService.deleteMainAd(mainAdId);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, "메인광고 삭제완료"));
    }

    /**
     * TODO : 주의점! 디테일 잡는부분은 광고index임 실수로 메인 index 안넣게 주의
     * @param ad_index
     * @param userDetails
     */
    @GetMapping("/main/detail/{ad_index}")
    public ResponseEntity<BfResponse<?>> detailMainAd(
            @PathVariable int ad_index,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        MainAdResponseDto mad = mainAdService.getMainAdDetail(ad_index, userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, mad));
    }

    @GetMapping("/main/available-dates")
    public ResponseEntity<BfResponse<?>> getAvailableDates(
            @RequestBody MainAdRequestDto mainAdDto){
        AvailableDatesResponseDto availableDates = mainAdService.getAvailableDates(mainAdDto);
        return ResponseEntity.ok(new BfResponse<>(availableDates));
    }


    //-------------배너 라인----------------------------------------------\

    @PostMapping("/banner")
    public ResponseEntity<BfResponse<String>> createBannerAd(
            @RequestBody BannerAdRequestDto bannerAdDto,
            @RequestPart("image") MultipartFile img,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bannerAdService.createBannerAd(bannerAdDto, img, userDetails);
        return ResponseEntity.ok(new BfResponse<>("배너 광고가 성공적으로 생성되었습니다."));
    }

    @PutMapping("/banner/{bannerId}")
    public ResponseEntity<BfResponse<String>> updateBannerAd(
            @PathVariable int bannerId,
            @RequestBody BannerAdRequestDto bannerAdDto,
            @RequestPart("image") MultipartFile img,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bannerAdService.updateBannerAd(bannerId, bannerAdDto, img, userDetails);
        return ResponseEntity.ok(new BfResponse<>("배너 광고가 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/banner/{bannerId}")
    public ResponseEntity<BfResponse<String>> deleteBannerAd(@PathVariable int bannerId) {
        bannerAdService.deleteBannerAd(bannerId);
        return ResponseEntity.ok(new BfResponse<>("배너 광고가 성공적으로 삭제되었습니다."));
    }

    @GetMapping("/banner/detail/{ad_index}")
    public ResponseEntity<BfResponse<?>> detailBannerAd(
            @PathVariable int ad_index,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        BannerAdResponseDto bad = bannerAdService.getBannerAdDetail(ad_index, userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, bad));
    }


    //-------------기타 라인----------------------------------------------

    @PostMapping("/announce")
    public ResponseEntity<BfResponse<?>> createAnnouncementAd(
            @RequestBody AnnouncementAdRequestDto announcementAdDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        announcementAdService.createAnnouncementAd(announcementAdDto, userDetails);
        return ResponseEntity.ok(new BfResponse<>("기본 광고 생성완료"));
    }

    @GetMapping("/announce/{announcementId}")
    public ResponseEntity<BfResponse<?>> getAnnouncementAdDetail(
            @PathVariable int announcementId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AnnouncementAdResponseDto announcementAd = announcementAdService.getAnnouncementAdDetail(announcementId, userDetails);
        return ResponseEntity.ok(new BfResponse<>(announcementAd));
    }

    @PutMapping("/announce/{announcementId}")
    public ResponseEntity<BfResponse<?>> updateAnnouncementAd(
            @PathVariable int announcementId,
            @RequestBody AnnouncementAdRequestDto announcementAdDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        announcementAdService.updateAnnouncementAd(announcementId, announcementAdDto, userDetails);
        return ResponseEntity.ok(new BfResponse<>("기본 광고 업데이트 완료"));
    }

    @DeleteMapping("/announce/{announcementId}")
    public ResponseEntity<BfResponse<?>> deleteAnnouncementAd(@PathVariable int announcementId) {
        announcementAdService.deleteAnnouncementAd(announcementId);
        return ResponseEntity.ok(new BfResponse<>("기본 광고 삭제완료"));
    }



    /*@PostMapping("/upload")
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
    }*/


}