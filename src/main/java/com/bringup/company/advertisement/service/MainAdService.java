package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.MainAdRequestDto;
import com.bringup.company.advertisement.dto.response.AvailableDatesResponseDto;
import com.bringup.company.advertisement.dto.response.MainAdResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.MainAdvertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.MainAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.exception.CompanyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class MainAdService {

    private final MainAdvertisementRepository mainAdvertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final ImageService imageService;

    private static final Map<Integer, BigDecimal> BASE_PRICES = Map.of(
            1, new BigDecimal(300),
            3, new BigDecimal(800),
            7, new BigDecimal(1100)
    );

    // 메인 광고 생성
    public void createMainAd(MainAdRequestDto mainAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(mainAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.getRecruitment().setRecruitmentIndex(mainAdDto.getRecruitmentIndex());
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);

        // 메인 광고 등록
        MainAdvertisement mainAd = new MainAdvertisement();
        mainAd.setAdvertisement(advertisement);
        mainAd.setMain_Image(imageService.saveImage(img));
        mainAd.setExposureDays(mainAdDto.getExposureDays());
        mainAd.setStartDate(mainAdDto.getStartDate());
        mainAd.setEndDate(mainAdDto.getStartDate().plusDays(mainAdDto.getExposureDays()));

        mainAdvertisementRepository.save(mainAd);
    }

    // 메인 광고 수정
    public void updateMainAd(int mainAdId, MainAdRequestDto mainAdDto, UserDetailsImpl userDetails) {
        MainAdvertisement mainAd = mainAdvertisementRepository.findById(mainAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!mainAd.getAdvertisement().getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 정보 업데이트
        Advertisement ad = mainAd.getAdvertisement();
        ad.setStatus(StatusType.CRT_WAIT); // 수정 시에도 초기 상태로 변경
        mainAd.setExposureDays(mainAdDto.getExposureDays());
        mainAd.setStartDate(mainAdDto.getStartDate());
        mainAd.setEndDate(mainAdDto.getStartDate().plusDays(mainAdDto.getExposureDays()));

        mainAdvertisementRepository.save(mainAd);
    }

    // 메인 광고 삭제
    public void deleteMainAd(int mainAdId) {
        MainAdvertisement mainAd = mainAdvertisementRepository.findById(mainAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = mainAd.getAdvertisement();
        ad.setStatus(StatusType.DEL_WAIT); // 상태를 삭제 대기로 변경

        mainAdvertisementRepository.save(mainAd);
    }

    // 메인 광고 상세 조회
    public MainAdResponseDto getMainAdDetail(int mainAdId, UserDetailsImpl userDetails) {
        MainAdvertisement mainAd = mainAdvertisementRepository.findById(mainAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = mainAd.getAdvertisement();

        // 광고 소유자인지 확인
        if (!ad.getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        return convertToDto(mainAd, ad);
    }

    public AvailableDatesResponseDto getAvailableDates(MainAdRequestDto mainAdDto) {
        LocalDate requestedStartDate = mainAdDto.getStartDate();
        int exposureDays = mainAdDto.getExposureDays();

        List<LocalDate> requestedDates = requestedStartDate.datesUntil(requestedStartDate.plusDays(exposureDays))
                .collect(Collectors.toList());

        // 이미 예약된 날짜 조회
        List<LocalDate> reservedDates = mainAdvertisementRepository.findReservedDates(requestedStartDate, requestedStartDate.plusDays(exposureDays));

        // 예약 가능한 날짜 필터링
        List<LocalDate> availableDates = requestedDates.stream()
                .filter(date -> !reservedDates.contains(date))
                .collect(Collectors.toList());

        // 사용 가능한 날짜 수에 따른 할인율 계산
        BigDecimal discountRate = calculateDiscount(exposureDays, availableDates.size());

        // 기본 가격과 할인율 적용
        BigDecimal basePrice = BASE_PRICES.get(exposureDays);
        BigDecimal finalPrice = basePrice.multiply(BigDecimal.ONE.subtract(discountRate));

        // 응답 DTO 생성
        return new AvailableDatesResponseDto(availableDates, null, discountRate, finalPrice);
    }

    /**
     * 할인율 계산 (사용 가능한 날짜 수에 따라)
     *
     * @param requestedDays 요청한 일수
     * @param availableDays 예약 가능한 일수
     * @return 할인율
     */
    private BigDecimal calculateDiscount(int requestedDays, int availableDays) {
        if (requestedDays == availableDays) {
            return BigDecimal.ZERO; // 할인 없음
        }
        return BigDecimal.valueOf((double) (requestedDays - availableDays) / requestedDays);
    }


    private MainAdResponseDto convertToDto(MainAdvertisement mainAd, Advertisement ad) {
        return MainAdResponseDto.builder()
                .mainId(mainAd.getMainId())
                .recruitmentIndex(ad.getRecruitment().getRecruitmentIndex())
                .startDate(mainAd.getStartDate())
                .endDate(mainAd.getEndDate())
                .exposureDays(mainAd.getExposureDays())
                .discountRate(mainAd.getDiscountRate())
                .viewCount(ad.getV_count())
                .clickCount(ad.getC_count())
                .status(ad.getStatus())
                .build();
    }
}
