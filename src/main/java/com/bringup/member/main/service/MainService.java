
package com.bringup.member.main.service;
import com.bringup.common.enums.StatusType;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.BannerAdvertisement;
import com.bringup.company.advertisement.entity.MainAdvertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.TimeSlot;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.BannerAdvertisementRepository;
import com.bringup.company.advertisement.repository.MainAdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.main.dto.*;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {
    private final UserRepository userRepository;
    private final PremiumAdvertisementRepository premiumAdvertisementRepository;
    private final CompanyRepository companyRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final BannerAdvertisementRepository bannerAdvertisementRepository;
    private final MainAdvertisementRepository mainAdvertisementRepository;
    private final CVRepository  cvRepository;
    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public MemberInfoDto getMemberInfo(UserDetailsImpl userDetails) {
        // 유저 정보 가져오기
        UserEntity userEntity = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + userDetails.getUsername()));

        // 유저의 인덱스(userIndex)를 기반으로 이력서 정보 가져오기
        List<CVEntity> cvList = cvRepository.findAllByUserIndex(userEntity.getUserIndex());

        // 메인 이력서 찾기 (mainCv == true)
        CVEntity mainCv = null;
        for (CVEntity cv : cvList) {
            if (cv.isMainCv()) {  // 여기서 isMainCv() 사용
                mainCv = cv;
                break;
            }
        }

        // 메인 이력서를 찾지 못한 경우 예외 처리
        if (mainCv == null) {
            throw new IllegalArgumentException("Main CV not found for user: " + userDetails.getUsername());
        }

        // DTO로 변환하여 반환
        return MemberInfoDto.builder()
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .cvImage(mainCv.getCvImage())
                .skills(mainCv.getSkill())  // "java, c, c++" 형식으로 가져옴
                .build();
    }
 /*   @Transactional
    public PremiumAdvertisementDto getPremiumAdvertisement() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // 오늘 날짜에 해당하는 광고들을 가져옴
        List<PremiumAdvertisement> availableAds = premiumAdvertisementRepository.findAvailableAds(today);

        if (availableAds.isEmpty()) {
            return null; // 사용 가능한 광고가 없을 경우
        }

        // 현재 시간에 맞는 광고 필터링
        for (PremiumAdvertisement ad : availableAds) {
            // DB에서 저장된 문자열을 TimeSlot으로 변환
            TimeSlot timeSlot = mapStringToTimeSlot(ad.getTimeSlot().name());

            // 해당 광고의 타임슬롯과 현재 시간이 맞는지 확인
            if (timeSlot != null && isTimeWithinSlot(timeSlot, now)) {
                // 광고 정보를 DTO로 변환하여 반환
                return convertToDto(ad);
            }
        }

        return null; // 현재 시간에 맞는 광고가 없을 경우
    }

    // DB에서 가져온 문자열을 TimeSlot Enum으로 매핑
    private TimeSlot mapStringToTimeSlot(String timeSlotStr) {
        switch (timeSlotStr) {
            case "01:00 ~ 04:00":
                return TimeSlot.P3_01_04;
            case "04:00 ~ 07:00":
                return TimeSlot.P1_04_07;
            case "07:00 ~ 10:00":
                return TimeSlot.GP_07_10;
            case "10:00 ~ 13:00":
                return TimeSlot.P2_10_13;
            case "13:00 ~ 16:00":
                return TimeSlot.P1_13_16;
            case "16:00 ~ 19:00":
                return TimeSlot.GP_16_19;
            case "19:00 ~ 22:00":
                return TimeSlot.P1_19_22;
            case "22:00 ~ 01:00":
                return TimeSlot.P3_22_01;
            default:
                throw new IllegalArgumentException("Unknown time slot: " + timeSlotStr);
        }
    }

    // TimeSlot에 따라 현재 시간이 해당 시간 범위에 속하는지 확인
    private boolean isTimeWithinSlot(TimeSlot timeSlot, LocalTime now) {
        switch (timeSlot) {
            case P3_01_04:
                return now.isAfter(LocalTime.of(1, 0)) && now.isBefore(LocalTime.of(4, 0));
            case P1_04_07:
                return now.isAfter(LocalTime.of(4, 0)) && now.isBefore(LocalTime.of(7, 0));
            case GP_07_10:
                return now.isAfter(LocalTime.of(7, 0)) && now.isBefore(LocalTime.of(10, 0));
            case P2_10_13:
                return now.isAfter(LocalTime.of(10, 0)) && now.isBefore(LocalTime.of(13, 0));
            case P1_13_16:
                return now.isAfter(LocalTime.of(13, 0)) && now.isBefore(LocalTime.of(16, 0));
            case GP_16_19:
                return now.isAfter(LocalTime.of(16, 0)) && now.isBefore(LocalTime.of(19, 0));
            case P1_19_22:
                return now.isAfter(LocalTime.of(19, 0)) && now.isBefore(LocalTime.of(22, 0));
            case P3_22_01:
                return (now.isAfter(LocalTime.of(22, 0)) || now.isBefore(LocalTime.of(1, 0)));
            default:
                throw new IllegalArgumentException("Unknown TimeSlot: " + timeSlot);
        }
    }

    // PremiumAdvertisement 엔티티를 DTO로 변환
    private PremiumAdvertisementDto convertToDto(PremiumAdvertisement ad) {
        PremiumAdvertisementDto dto = new PremiumAdvertisementDto();
        dto.setPremiumIndex(ad.getPremiumIndex());
        dto.setAdvertisement(ad.getAdvertisement().getAdvertisementIndex());
        dto.setRecruitmentIndex(ad.getAdvertisement().getRecruitment().getRecruitmentIndex());
        dto.setTimeSlot(ad.getTimeSlot());
        dto.setStartDate(ad.getStartDate());
        dto.setEndDate(ad.getEndDate());
        dto.setPremiumImage(ad.getImage());

        return dto;
    }*/

    @Transactional
    public List<MainAdvertisementDto> getMainAdvertisement() {
        LocalDate today = LocalDate.now();

        // 모든 광고 가져옴
        List<MainAdvertisement> allAds = mainAdvertisementRepository.findAll();

        // 오늘 날짜에 맞는 광고만 필터링
        List<MainAdvertisementDto> activeAds = new ArrayList<>();

        for (MainAdvertisement ad : allAds) {
            // 시작 날짜와 종료 날짜 조건을 확인
            if (!ad.getStartDate().isAfter(today) && !ad.getEndDate().isBefore(today)) {
                // 광고가 활성화된 기간 내에 있으면 리스트에 추가
                activeAds.add(new MainAdvertisementDto(
                        ad.getAdvertisement().getRecruitment().getRecruitmentIndex(),
                        ad.getMain_Image()
                ));
            }
        }

        // 필터링된 광고가 없으면 빈 리스트 반환
        if (activeAds.isEmpty()) {
            return Collections.emptyList();
        }


        Collections.shuffle(activeAds);// 광고 리스트를 랜덤으로 섞음

        // 최대 5개의 랜덤 광고를 반환
        List<MainAdvertisementDto> randomAds = new ArrayList<>();
        int count = 0;
        for (MainAdvertisementDto ad : activeAds) {
            randomAds.add(ad);
            count++;
            if (count == 5) { // 최대 5개까지만 추가
                break;
            }
        }

        return randomAds;
    }


    @Transactional
    public BannerAdvertisementDto getBannerAdvertisement() {
        LocalDate today = LocalDate.now();

        // 모든 배너 광고 가져옴
        List<BannerAdvertisement> allBanners = bannerAdvertisementRepository.findAll();

        // 오늘 날짜에 맞는 배너만 필터링
        List<BannerAdvertisement> activeBanners = new ArrayList<>();
        for (BannerAdvertisement banner : allBanners) {
            if (!banner.getStartDate().isAfter(today) && !banner.getEndDate().isBefore(today)) {
                activeBanners.add(banner);
            }
        }

        // 필터링된 배너가 없으면 null 반환
        if (activeBanners.isEmpty()) {
            return null;
        }

        // view_count가 가장 적은 배너를 찾음
        int minViewCount = Integer.MAX_VALUE;
        for (BannerAdvertisement banner : activeBanners) {
            int currentViewCount = banner.getAdvertisement().getV_count();
            if (currentViewCount < minViewCount) {
                minViewCount = currentViewCount;
            }
        }

        // view_count가 가장 적은 배너들을 리스트에 추가
        List<BannerAdvertisement> minViewBanners = new ArrayList<>();
        for (BannerAdvertisement banner : activeBanners) {
            if (banner.getAdvertisement().getV_count() == minViewCount) {
                minViewBanners.add(banner);
            }
        }

        // 랜덤으로 배너 하나 선택
        Random random = new Random();
        int randomIndex = random.nextInt(minViewBanners.size());
        BannerAdvertisement selectedBanner = minViewBanners.get(randomIndex);

        // 선택된 배너의 view_count 증가
        Advertisement selectedAd = selectedBanner.getAdvertisement();
        selectedAd.setV_count(selectedAd.getV_count() + 1);
        advertisementRepository.save(selectedAd);

        // DTO로 변환하여 반환 (공고 인덱스를 반환하도록 수정)
        BannerAdvertisementDto bannerDto = new BannerAdvertisementDto(
                selectedAd.getRecruitment().getRecruitmentIndex(), // 공고 인덱스 반환
                selectedBanner.getBanner_Image() // 배너 이미지 반환
        );

        return bannerDto;
    }



    public List<MainRecruitmentDto> getMainRecruitment() {
        List<Recruitment> activeRecruitments = recruitmentRepository.findAllByStatus(StatusType.ACTIVE);

        if (activeRecruitments.isEmpty()) {
            return new ArrayList<>();
        }

        List<MainRecruitmentDto> mainRecruitmentDtos = new ArrayList<>();

        for (Recruitment recruitment : activeRecruitments) {
            Optional<Company> companyOpt = companyRepository.findBycompanyId(recruitment.getCompany().getCompanyId());

            if (companyOpt.isPresent()) {
                Company company = companyOpt.get();

                String companyImg = company.getCompanyImg();

                if (companyImg.contains(",")) {
                    companyImg = companyImg.split(",")[0].trim();
                }

                // MainRecruitmentDto에 필요한 모든 정보를 설정
                MainRecruitmentDto dto = new MainRecruitmentDto();
                dto.setRecruitmentIndex(recruitment.getRecruitmentIndex());
                dto.setCompanyId(BigInteger.valueOf(company.getCompanyId()));
                dto.setCompanyName(company.getCompanyName()); // 기업명 설정
                dto.setRecruitmentTitle(recruitment.getRecruitmentTitle());
                dto.setRecruitmentType(recruitment.getRecruitmentType().name()); // 공고 형태 설정
                dto.setCategory(recruitment.getCategory());
                dto.setSkill(recruitment.getSkill());
                dto.setStartDate(recruitment.getStartDate());
                dto.setPeriod(recruitment.getPeriod());
                dto.setViewCount(recruitment.getViewCount());
                dto.setStatus(recruitment.getStatus());
                dto.setCompanyImg(companyImg); // 회사 이미지 설정

                mainRecruitmentDtos.add(dto);
            }
        }

        return mainRecruitmentDtos;
    }

}
