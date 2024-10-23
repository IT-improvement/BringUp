package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdvertisementRepository adrepo;

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

    /*public List<String> getUserAdvertisement(UserDetailsImpl userDetails){

    }*/

}
