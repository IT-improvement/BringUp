/*
package com.bringup.member.main.service;
import com.bringup.common.enums.StatusType;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.BannerAdvertisement;
import com.bringup.company.advertisement.entity.MainAdvertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.repository.BannerAdvertisementRepository;
import com.bringup.company.advertisement.repository.MainAdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.main.dto.*;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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

    private int currentAdIndex = 0; // 현재 광고 인덱스를 관리

    public MemberInfoDto getMemberInfo(UserDetailsImpl userDetails) {
        // userDetails에서 이메일을 가져와서 해당 이메일로 유저 정보를 조회
        UserEntity userEntity = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + userDetails.getUsername()));

        // 조회한 유저 정보를 DTO로 변환
        return MemberInfoDto.builder()
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .build();
    }

    // 선착순으로 매진되지 않은 광고 하나를 가져오는 메서드
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public PremiumAdvertisementDto getSinglePremiumAdvertisement() {
        // 현재 시간 가져오기
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        // 1. 날짜가 지난 광고를 매진 처리 (is_sold_out = 0)
        List<PremiumAdvertisement> expiredAdvertisements = premiumAdvertisementRepository.findAllByEndDateBefore(today);
        for (PremiumAdvertisement ad : expiredAdvertisements) {
            ad.setSoldOut(false); // 매진 처리 (0)
            premiumAdvertisementRepository.save(ad);  // 상태 업데이트 저장
        }

        // 2. 매진되지 않은 광고만 가져옴 (is_sold_out = 1)
        List<PremiumAdvertisement> availableAds = premiumAdvertisementRepository.findAllByIsSoldOutTrueOrderByPremiumIdAsc();

        if (availableAds.isEmpty()) {
            return null; // 매진되지 않은 광고가 없으면 null 반환
        }

        // 3. 선착순으로 하나의 광고만 선택
        PremiumAdvertisement premiumAd = availableAds.get(currentAdIndex);

        // 다음 요청 시 다음 광고가 나오도록 인덱스 증가
        currentAdIndex = (currentAdIndex + 1) % availableAds.size();

        // 4. DTO로 변환하여 반환
        PremiumAdvertisementDto dto = new PremiumAdvertisementDto();
        dto.setPremiumId(premiumAd.getPremiumId());
        dto.setAdvertisement(premiumAd.getAdvertisement().getAdvertisementIndex());
        dto.setRecruitmentIndex(premiumAd.getAdvertisement().getRecruitmentIndex());
        dto.setTimeSlot(premiumAd.getTimeSlot());
        dto.setStartDate(premiumAd.getStartDate());
        dto.setEndDate(premiumAd.getEndDate());
        dto.setPremiumImage(premiumAd.getPremiumImage());

        return dto;
    }

    // 선착순으로 먼저 신청한 광고 6개를 가져오는 메서드
    public List<MainAdvertisementDto> getMainAdvertisement() {

        List<MainAdvertisement> firstAdvertisements = mainAdvertisementRepository.findTop6ByOrderByMainIdAsc();

        List<MainAdvertisementDto> result = new ArrayList<>();

        for (MainAdvertisement ad : firstAdvertisements) {
            // 필요한 값들을 각 변수에 저장
            int mainId = ad.getMainId();
            String mainImage = ad.getMainImage();
            int recruitmentIndex = ad.getAdvertisement().getRecruitmentIndex();  // 모집 인덱스

            // DTO에 변수 값을 할당하여 추가
            MainAdvertisementDto dto = new MainAdvertisementDto();
            dto.setMainId(mainId);
            dto.setMainImage(mainImage);
            dto.setRecruitmentIndex(recruitmentIndex);  // 모집 인덱스 추가


            result.add(dto);
        }
        return result;
    }


    // 선착순으로 광고를 가져오는 메서드
    public List<BannerAdvertisementDto> getBannerAdvertisement() {

        List<BannerAdvertisement> bannerAdvertisements = bannerAdvertisementRepository.findAllByOrderByBannerIdAsc();

        List<BannerAdvertisementDto> result = new ArrayList<>();

        for (BannerAdvertisement bannerAd : bannerAdvertisements) {
            int bannerId = bannerAd.getBannerId();
            String bannerImage = bannerAd.getBannerImage();
            int exposureDays = bannerAd.getExposureDays();


            // DTO 생성 및 리스트에 추가
            BannerAdvertisementDto dto = new BannerAdvertisementDto();
            dto.setBannerId(bannerId);
            dto.setBannerImage(bannerImage);
            dto.setExposureDays(exposureDays);



            result.add(dto);
        }

        return result;
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


}*/
