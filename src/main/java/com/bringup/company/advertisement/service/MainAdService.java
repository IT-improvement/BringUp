package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.ChoicedateRequestDto;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
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

    public List<String> getUnavailableDates(ChoicedateRequestDto dto) {
        // DateTimeFormatter를 사용하여 String을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // String으로 받은 날짜를 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(dto.getStartdate(), formatter);
        LocalDate endDate = LocalDate.parse(dto.getEnddate(), formatter);

        System.out.println("startdate : " + startDate);
        System.out.println("enddate : " + endDate);

        // 해당 기간에 예약된 광고들을 가져옴
        List<MainAdvertisement> reservedAds = mainAdvertisementRepository
                .findReservedAds(startDate, endDate);

        System.out.println("reservedAds : " + reservedAds);
        // 로그 추가 - 조회된 광고 리스트 확인
        System.out.println("조회된 광고 개수: " + reservedAds.size());
        for (MainAdvertisement ad : reservedAds) {
            System.out.println("광고 ID: " + ad.getMainId() + ", 시작 날짜: " + ad.getStartDate() + ", 종료 날짜: " + ad.getEndDate());
        }

        // 광고 수를 카운트할 맵 (날짜별로 광고 개수 저장)
        Map<LocalDate, Integer> adCountMap = new HashMap<>();

        // 예약된 광고에서 각 날짜에 해당하는 광고 수를 카운트
        for (MainAdvertisement ad : reservedAds) {
            LocalDate adStartDate = ad.getStartDate();
            LocalDate adEndDate = ad.getEndDate();

            // 광고의 날짜 범위가 요청된 날짜 범위와 겹치는지 확인
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                if (!date.isBefore(adStartDate) && !date.isAfter(adEndDate)) {
                    adCountMap.put(date, adCountMap.getOrDefault(date, 0) + 1);
                    System.out.println("광고 카운트 맵 업데이트: " + date + " -> " + adCountMap.get(date));
                }
            }
        }

        // 요청한 날짜 범위를 돌면서 광고가 6개 이상인 날짜를 찾음
        List<String> unavailableDates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            System.out.println("날짜 확인: " + date + " -> 광고 수: " + adCountMap.getOrDefault(date, 0));
            if (adCountMap.getOrDefault(date, 0) >= 6) {
                unavailableDates.add(date.format(formatter));  // 날짜를 문자열로 변환하여 리스트에 추가
                System.out.println("겹치는 날짜 추가됨: " + date);
            }
        }

        // 불가능한 날짜들을 반환
        return unavailableDates;
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
