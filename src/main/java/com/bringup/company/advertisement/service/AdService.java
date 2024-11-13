package com.bringup.company.advertisement.service;

import com.bringup.admin.payment.entity.Payment;
import com.bringup.admin.payment.repository.PaymentRepository;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.response.UserAdvertisementResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdvertisementRepository adrepo;
    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository companyRepository;
    private final PaymentRepository paymentRepository;

    // 매일 00시에 실행: 당일 시작하는 광고를 ACTIVE 상태로 전환
    @Scheduled(cron = "0 0 0 * * ?") // 매일 00:00에 실행
    public void setActiveAds(){
        LocalDate today = LocalDate.now();

        // startDate가 오늘이고 상태가 CRT_WAIT인 광고들 찾기
        List<Advertisement> adsToActivate = adrepo.findAllByStatusAndStartDate(StatusType.CRT_WAIT, today);

        if (adsToActivate.isEmpty()) {
            throw new AdvertisementException(NOT_FOUND_ADVERTISEMENT);
        }

        // 상태를 ACTIVE로 변경
        adsToActivate.forEach(ad -> ad.setStatus(StatusType.ACTIVE));
        adrepo.saveAll(adsToActivate);
    }
    // 매일 00시 이후 실행: 종료일이 오늘인 광고들을 INACTIVE 상태로 전환
    @Scheduled(cron = "0 1 0 * * ?") // 매일 00:01에 실행
    public void setInactiveAds(){
        LocalDate today = LocalDate.now();

        // endDate가 오늘이고 상태가 ACTIVE인 광고들 찾기
        List<Advertisement> adsToDeactivate = adrepo.findAllByStatusAndEndDate(StatusType.ACTIVE, today);

        if (adsToDeactivate.isEmpty()) {
            throw new AdvertisementException(NOT_FOUND_ADVERTISEMENT);
        }

        // 상태를 INACTIVE로 변경
        adsToDeactivate.forEach(ad -> ad.setStatus(StatusType.INACTIVE));
        adrepo.saveAll(adsToDeactivate);
    }

    public List<UserAdvertisementResponseDto> getUserAdvertisement(UserDetailsImpl userDetails) {

        // 사용자 회사 정보 조회
        Company company = companyRepository.findBycompanyId(userDetails.getId())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_MEMBER_ID));

        // 해당 회사의 모든 광고 조회
        List<Advertisement> ads = advertisementRepository.findAllByRecruitmentCompany(company);

        // 결제 내역 조회 및 맵핑 (광고 인덱스 기준)
        Map<Integer, Integer> advertisementPayments = paymentRepository.findByUserIdxAndRoles(company.getCompanyId(), RolesType.ROLE_COMPANY)
                .stream()
                .collect(Collectors.toMap(
                        payment -> payment.getItemIdx().getItemIndex(),
                        payment -> payment.getItemIdx().getPrice().intValue()
                ));

        // 광고 목록을 UserAdvertisementResponseDto로 변환
        return ads.stream().map(ad -> {
            // 광고 타입 결정
            String adType = determineAdType(ad);

            // 결제 금액 조회 (없으면 0)
            int paymentAmount = advertisementPayments.getOrDefault(ad.getAdvertisementIndex(), 0);

            return UserAdvertisementResponseDto.builder()
                    .adIdx(ad.getAdvertisementIndex())
                    .adType(adType)
                    .recruitmentTitle(ad.getRecruitment().getRecruitmentTitle())
                    .startDate(ad.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .endDate(ad.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .clickCount(ad.getC_count())
                    .payed(paymentAmount)
                    .build();
        }).collect(Collectors.toList());
    }


    // 광고 타입을 결정하는 메소드
    private String determineAdType(Advertisement ad) {
        if (ad.getPremiumAdvertisement() != null) {
            return "Premium";
        } else if (ad.getMainAdvertisement() != null) {
            return "Main";
        } else if (ad.getBannerAdvertisement() != null) {
            return "Banner";
        } else if (ad.getAnnouncementAdvertisement() != null) {
            return "Announcement";
        } else {
            return "Unknown";
        }
    }

}
