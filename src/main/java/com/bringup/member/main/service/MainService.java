package com.bringup.member.main.service;
import com.bringup.common.enums.StatusType;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.main.dto.CompanyImageDto;
import com.bringup.member.main.dto.MainRecruitmentDto;
import com.bringup.member.main.dto.PremiumAdvertisementDto;
import com.bringup.member.main.dto.MemberInfoDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public List<PremiumAdvertisementDto> getPremiumAdvertisement() {
        // 현재 시간 가져오기
        LocalTime now = LocalTime.now();

        // 모든 프리미엄 광고를 가져와서 현재 시간과 일치하는 광고 필터링
        List<PremiumAdvertisement> premiumAdvertisements = premiumAdvertisementRepository.findAll();

        List<PremiumAdvertisementDto> result = new ArrayList<>();
        for (PremiumAdvertisement premiumAd : premiumAdvertisements) {
            // 엔티티 정보 각 변수에 할당
            int premiumId = premiumAd.getPremiumId();
            Advertisement ad = premiumAd.getAdvertisement();
            int advertisementIndex = ad.getAdvertisementIndex();
            String adType = premiumAd.getAdType();
            int price = premiumAd.getPrice();
            String timeSlot = premiumAd.getTimeSlot();
            boolean isSoldOut = premiumAd.isSoldOut();
            LocalTime startTime = LocalTime.parse(timeSlot.split(" ~ ")[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(timeSlot.split(" ~ ")[1], DateTimeFormatter.ofPattern("HH:mm"));
            LocalDate startDate = premiumAd.getStartDate();
            LocalDate endDate = premiumAd.getEndDate();
            String premiumImage = ad.getAdvertisementImage();

            // 현재 시간과 광고 시간대가 일치하는지 확인
            if (now.isAfter(startTime) && now.isBefore(endTime)) {
                // DTO로 변환하여 리스트에 추가
                PremiumAdvertisementDto dto = PremiumAdvertisementDto.builder()
                        .premiumId(premiumId)
                        .advertisementIndex(advertisementIndex)
                        .adType(adType)
                        .price(price)
                        .timeSlot(timeSlot)
                        .isSoldOut(isSoldOut)
                        .startDate(startDate)
                        .endDate(endDate)
                        .premiumImage(premiumImage)
                        .build();
                result.add(dto);
            }
        }

        return result;
    }

    // 회사 이미지 데이터를 가져오는 메서드 (ACTIVE 상태인 회사만 랜덤으로 최대 6개 가져오기)
    public List<CompanyImageDto> getActiveCompanyImages() {
        List<Company> activeCompanies = companyRepository.findAllByStatus(StatusType.ACTIVE);

        if (activeCompanies.isEmpty()) {
            return new ArrayList<>(); // 회사 목록이 비어 있을 경우 빈 리스트 반환
        }

        List<CompanyImageDto> companyImageList = new ArrayList<>();
        Random random = new Random();

        // 최대 6개의 랜덤한 회사 선택 (단, 회사 수가 6개 미만일 경우 그만큼만 선택)
        int maxCompanies = Math.min(6, activeCompanies.size());
        Set<Integer> selectedIndices = new HashSet<>(); // 중복 방지를 위한 인덱스 저장

        while (selectedIndices.size() < maxCompanies) {
            int randomIndex = random.nextInt(activeCompanies.size());
            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Company company = activeCompanies.get(randomIndex);

                // companyImg에서 첫 번째 이미지 URL만 가져오기
                String companyImg = company.getCompanyImg();
                if (companyImg.contains(",")) {
                    companyImg = companyImg.split(",")[0]; // 첫 번째 이미지 URL만 사용
                }

                // 회사명과 이미지 정보를 DTO에 설정
                CompanyImageDto dto = new CompanyImageDto(
                        company.getCompanyId(),
                        company.getCompanyName(), // companyName 필드 추가
                        companyImg // 첫 번째 이미지 URL만 설정
                );
                companyImageList.add(dto);
            }
        }

        return companyImageList;
    }
/*
    // 광고 목록 중 ACTIVE 상태이고, 타입이 MAIN인 광고를 랜덤으로 최대 3개 가져오는 메서드
    public List<PremiumAdvertisementDto> getAd3Advertisements() {
        List<Advertisement> mainAdvertisements = advertisementRepository.findAllByStatus(StatusType.ACTIVE);

        if (mainAdvertisements.isEmpty()) {
            return new ArrayList<>(); // MAIN 광고가 없을 경우 빈 리스트 반환
        }

        List<PremiumAdvertisementDto> randomMainAdvertisements = new ArrayList<>();
        Random random = new Random();
        int maxAdvertisements = Math.min(3, mainAdvertisements.size()); // 최대 3개까지 선택

        Set<Integer> selectedIndices = new HashSet<>(); // 중복 방지를 위한 인덱스 저장

        while (selectedIndices.size() < maxAdvertisements) {
            int randomIndex = random.nextInt(mainAdvertisements.size());
            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Advertisement ad = mainAdvertisements.get(randomIndex);
                randomMainAdvertisements.add(convertToDto(ad));
            }
        }

        return randomMainAdvertisements;
    }*/

    @Transactional(readOnly = true)
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