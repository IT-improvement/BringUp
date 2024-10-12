package com.bringup.company.advertisement.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.ChoicedateRequestDto;
import com.bringup.company.advertisement.dto.request.PremiumAdRequestDto;
import com.bringup.company.advertisement.dto.response.AvailableDatesResponseDto;
import com.bringup.company.advertisement.dto.response.PremiumAdResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.Ad_Type;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class PremiumAdService {

    private final ImageService imageService;
    private final PremiumAdvertisementRepository premiumAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository companyRepository;
    private final RecruitmentRepository recruitmentRepository;

    public List<String> getUnavailableDates(ChoicedateRequestDto choicedatedto) {

        // DateTimeFormatter를 사용하여 String을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // String으로 받은 날짜를 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(choicedatedto.getStartdate(), formatter);
        LocalDate endDate = LocalDate.parse(choicedatedto.getEnddate(), formatter);

        // 해당 기간에 예약된 프리미엄 광고들을 가져옴
        List<PremiumAdvertisement> reservedAds = premiumAdvertisementRepository
                .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);

        // 예약 불가능한 날짜 및 시간을 저장할 리스트
        List<String> unavailableDates = new ArrayList<>();

        // 각 날짜별로 확인
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String[] timeSlots = {
                    "01:00 ~ 04:00",
                    "04:00 ~ 07:00",
                    "07:00 ~ 10:00",
                    "10:00 ~ 13:00",
                    "13:00 ~ 16:00",
                    "16:00 ~ 19:00",
                    "19:00 ~ 22:00",
                    "22:00 ~ 01:00"
            };

            // 각 시간대별로 예약 여부를 확인
            for (String timeSlot : timeSlots) {
                boolean isTimeSlotUnavailable = false;

                for (PremiumAdvertisement ad : reservedAds) {
                    if (ad.getStartDate().equals(date) && ad.getTimeSlot().equals(timeSlot)) {
                        isTimeSlotUnavailable = true;
                        break;  // 예약된 시간대를 찾으면 더 이상 확인하지 않음
                    }
                }

                // 예약 불가능한 시간대를 리스트에 추가
                if (isTimeSlotUnavailable) {
                    unavailableDates.add(date + " (" + timeSlot + ")");
                }
            }
        }

        return unavailableDates;  // 예약 불가능한 날짜 및 시간대 반환
    }

    public void createPremiumAd(PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // display_startDate와 display_endDate 범위 내의 날짜를 문자열로 변환하여 저장
        LocalDate displayStartDate = LocalDate.parse(premiumAdDto.getDisplay_startDate());
        LocalDate displayEndDate = LocalDate.parse(premiumAdDto.getDisplay_endDate());
        String displayDates = convertDatesToString(displayStartDate, displayEndDate);

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitment(recruitment);
        advertisement.setDisplay(displayDates);
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);

        // 프리미엄 광고 정보 등록
        PremiumAdvertisement premiumAd = new PremiumAdvertisement();
        premiumAd.setAdvertisement(advertisement);
        premiumAd.setAdType(premiumAdDto.getAdType()); // enum 사용
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot());
        premiumAd.setImage(imageService.saveImage(img));
        premiumAd.setStartDate(premiumAdDto.getStartDate());
        premiumAd.setEndDate(premiumAdDto.getEndDate());

        premiumAdvertisementRepository.save(premiumAd);
        // TODO: 웹소켓으로 어드민소통 & 결제를 구성해야함 일단 그냥 CRT_WAIT으로 생성
    }

    public void updatePremiumAd(int premiumAdId, PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = advertisementRepository.findById(premiumAd.getAdvertisement().getAdvertisementIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        // display_startDate와 display_endDate 범위 내의 날짜를 문자열로 변환하여 저장
        LocalDate displayStartDate = LocalDate.parse(premiumAdDto.getDisplay_startDate());
        LocalDate displayEndDate = LocalDate.parse(premiumAdDto.getDisplay_endDate());
        String displayDates = convertDatesToString(displayStartDate, displayEndDate);

        // 광고 정보 업데이트
        ad.setStatus(StatusType.CRT_WAIT);
        ad.setDisplay(displayDates);
        premiumAd.setAdType(premiumAdDto.getAdType());
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot());
        premiumAd.setImage(imageService.saveImage(img)); // 이미지를 새로 저장
        premiumAd.setStartDate(premiumAdDto.getStartDate());
        premiumAd.setEndDate(premiumAdDto.getEndDate());

        premiumAdvertisementRepository.save(premiumAd);
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 CRT_WAIT으로 생성
    }

    public void deletePremiumAd(int premiumAdId) {
        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = advertisementRepository.findById(premiumAd.getAdvertisement().getAdvertisementIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));
        ad.setStatus(StatusType.DEL_WAIT);
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 삭제대기로 변경
    }

    public PremiumAdResponseDto getPremiumAdDetail(UserDetailsImpl userDetails, Integer ad_index) {
        Advertisement ad = advertisementRepository.findByAdvertisementIndex(ad_index)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        PremiumAdvertisement premiumAdvertisement = premiumAdvertisementRepository.findById(ad.getPremiumAdvertisement().getPremiumIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CompanyException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        return convertToDto(ad, premiumAdvertisement);
    }

    private PremiumAdResponseDto convertToDto(Advertisement ad, PremiumAdvertisement pad) {
        return PremiumAdResponseDto.builder()
                .advertisementIndex(ad.getAdvertisementIndex())
                .recruitmentIndex(ad.getRecruitment().getRecruitmentIndex())
                .status(ad.getStatus())
                .adType(pad.getAdType())
                .timeSlot(pad.getTimeSlot())
                .startDate(pad.getStartDate())
                .endDate(pad.getEndDate())
                .ad_img(pad.getImage())
                .view_count(ad.getV_count())
                .click_count(ad.getC_count())
                .build();
    }
/*
    public List<LocalDate> getSoldOutDates() {
        return premiumAdvertisementRepository.findSoldOutDates();
    }*/

    // 날짜 리스트를 콤마로 구분된 문자열로 변환
    private String convertDatesToString(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dateList = startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());

        // 날짜 리스트를 "yyyy-MM-dd" 형식의 문자열로 변환
        return dateList.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(", "));
    }

}
