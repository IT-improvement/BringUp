package com.bringup.company.advertisement.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.PremiumAdRequestDto;
import com.bringup.company.advertisement.dto.response.AvailableDatesResponseDto;
import com.bringup.company.advertisement.dto.response.PremiumAdResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.TimeSlot;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.*;
import static com.bringup.company.advertisement.enums.TimeSlot.*;

@Service
@RequiredArgsConstructor
public class PremiumAdService {

    private final ImageService imageService;
    private final PremiumAdvertisementRepository premiumAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository companyRepository;
    private final RecruitmentRepository recruitmentRepository;

    public AvailableDatesResponseDto getAvailableTimeSlotsAndDiscount(PremiumAdRequestDto premiumAdDto) {

        LocalDate endDate = premiumAdDto.getEndDate();
        LocalDate startDate = premiumAdDto.getStartDate();

        // 해당 타임슬롯과 기간에 예약된 프리미엄 광고를 가져옴
        List<PremiumAdvertisement> reservedAds = premiumAdvertisementRepository
                .findAllByTimeSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        premiumAdDto.getTimeSlot(), startDate, endDate);

        // 예약된 타임슬롯에 해당하는 날짜를 수집
        List<LocalDate> soldOutDates = reservedAds.stream()
                .flatMap(ad -> ad.getStartDate().datesUntil(ad.getEndDate().plusDays(1))) // startDate부터 endDate까지
                .collect(Collectors.toList());

        // 사용 가능한 날짜 계산 (요청된 기간에서 매진된 날짜 제외)
        List<LocalDate> availableDates = startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> !soldOutDates.contains(date)) // 매진된 날짜는 제외
                .collect(Collectors.toList());

        // 할인율 계산
        int totalDays = 3;
        int availableDays = availableDates.size();
        BigDecimal discountRate = calculateDiscountRate(totalDays, availableDays);

        // 기본 가격 계산 (타임슬롯에 따라)
        BigDecimal basePrice = getBasePrice(premiumAdDto.getTimeSlot());
        BigDecimal finalPrice = calculateFinalPrice(basePrice, totalDays, availableDays, discountRate);

        // 매진된 날짜와 예약 가능한 날짜 반환
        return AvailableDatesResponseDto.builder()
                .availableDates(availableDates)
                .soldOutDates(soldOutDates)
                .discountRate(discountRate)
                .finalPrice(finalPrice)
                .build();
    }

    private BigDecimal calculateDiscountRate(int totalDays, int availableDays) {
        if (totalDays > availableDays) {
            return BigDecimal.valueOf(((totalDays - availableDays) / (double) totalDays) * 100);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal calculateFinalPrice(BigDecimal basePrice, int totalDays, int availableDays, BigDecimal discountRate) {
        return basePrice.multiply(BigDecimal.valueOf(availableDays))
                .multiply(BigDecimal.ONE.subtract(discountRate.divide(BigDecimal.valueOf(100))));
    }

    private BigDecimal getBasePrice(TimeSlot timeSlot) {
        switch (timeSlot) {
            case GP_07_10:
            case GP_16_19:
                return BigDecimal.valueOf(240); // GP 기본 가격
            case P1_04_07:
            case P1_13_16:
            case P1_19_22:
                return BigDecimal.valueOf(210); // P1 기본 가격
            case P2_10_13:
                return BigDecimal.valueOf(180); // P2 기본 가격
            case P3_01_04:
            case P3_22_01:
                return BigDecimal.valueOf(150); // P3 기본 가격
            default:
                throw new IllegalArgumentException("Unknown time slot: " + timeSlot);
        }
    }

    public void createPremiumAd(PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if(!recruitment.getCompany().getCompanyId().equals(userDetails.getId())){
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitment(recruitment);
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);

        // 프리미엄 광고 정보 등록
        PremiumAdvertisement premiumAd = new PremiumAdvertisement();
        premiumAd.setAdvertisement(advertisement);
        premiumAd.setAdType(premiumAdDto.getAdType()); // enum 사용
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot()); // enum 사용
        premiumAd.setImage(imageService.saveImage(img));
        premiumAd.setStartDate(premiumAdDto.getStartDate());
        premiumAd.setEndDate(premiumAdDto.getEndDate());

        premiumAdvertisementRepository.save(premiumAd);
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 CRT_WAIT으로 생성
    }

    public void updatePremiumAd(int premiumAdId, PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if(!recruitment.getCompany().getCompanyId().equals(userDetails.getId())){
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = advertisementRepository.findById(premiumAd.getAdvertisement().getAdvertisementIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        // 광고 정보 업데이트
        ad.setStatus(StatusType.CRT_WAIT);
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

    public PremiumAdResponseDto getPremiumAdDetail(UserDetailsImpl userDetails, Integer ad_index){
        Advertisement ad = advertisementRepository.findByAdvertisementIndex(ad_index)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        PremiumAdvertisement premiumAdvertisement = premiumAdvertisementRepository.findById(ad.getPremiumAdvertisement().getPremium_index())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CompanyException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        return convertToDto(ad, premiumAdvertisement);
    }

    private PremiumAdResponseDto convertToDto(Advertisement ad, PremiumAdvertisement pad){
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

}
