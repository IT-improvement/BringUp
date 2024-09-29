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

    //프리미엄 광고 사진가져오는 메서드
    public List<PremiumAdvertisementDto> getPremiumAdvertisement() {
        // 현재 시간 가져오기
        LocalTime now = LocalTime.now();

        // 모든 프리미엄 광고를 가져와서 현재 시간과 일치하는 광고 필터링
        List<PremiumAdvertisement> premiumAdvertisements = premiumAdvertisementRepository.findAll();

        List<PremiumAdvertisementDto> result = new ArrayList<>();
        for (PremiumAdvertisement premiumAd : premiumAdvertisements) {
            // 엔티티 정보 각 변수에 할당
            int premiumId = premiumAd.getPremiumId();
            String timeSlot = premiumAd.getTimeSlot();
            LocalTime startTime = LocalTime.parse(timeSlot.split(" ~ ")[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(timeSlot.split(" ~ ")[1], DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate startDate = premiumAd.getStartDate();
            LocalDate endDate = premiumAd.getEndDate();
            String premiumImage = premiumAd.getPremiumImage();

            // 현재 시간과 광고 시간대가 일치하는지 확인
            if (now.isAfter(startTime) && now.isBefore(endTime)) {
                // DTO로 변환하여 리스트에 추가
                PremiumAdvertisementDto dto = new PremiumAdvertisementDto();
                        dto.setPremiumId(premiumId);
                        dto.setTimeSlot(timeSlot);
                        dto.setStartDate(startDate);
                        dto.setEndDate(endDate);
                        dto.setPremiumImage(premiumImage);
                result.add(dto);
            }
        }

        return result;
    }

    // 선착순으로 먼저 신청한 광고 6개를 가져오는 메서드
    public List<MainAdvertisementDto> getMainAdvertisement() {

        List<MainAdvertisement> firstAdvertisements = mainAdvertisementRepository.findTop6ByOrderByMainIdAsc();

        List<MainAdvertisementDto> result = new ArrayList<>();

        for (MainAdvertisement ad : firstAdvertisements) {
            // 필요한 값들을 각 변수에 저장
            int mainId = ad.getMainId();
            String mainImage = ad.getMainImage();

            // DTO에 변수 값을 할당하여 추가
            MainAdvertisementDto dto = new MainAdvertisementDto();
            dto.setMainId(mainId);
            dto.setMainImage(mainImage);

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


}