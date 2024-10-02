package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.company.advertisement.dto.request.PremiumAdRequestDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.TimeSlot;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PremiumAdService {

    private final ImageService imageService;
    private final PremiumAdvertisementRepository premiumAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;

    public List<TimeSlot> getAvailableTimes(LocalDate date) {
        List<TimeSlot> allTimeSlots = Arrays.asList(TimeSlot.values());

        // 해당 날짜에 이미 매진된 시간대를 조회
        List<TimeSlot> soldOutSlots = premiumAdvertisementRepository.findSoldOutTimeSlotsByStartDate(date);

        // 매진되지 않은 시간대만 반환
        return allTimeSlots.stream()
                .filter(slot -> !soldOutSlots.contains(slot))
                .collect(Collectors.toList());
    }

    public void createPremiumAd(PremiumAdRequestDto premiumAdDto, MultipartFile img) {
        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitmentIndex(premiumAdDto.getRecruitmentIndex());
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
    }

    public PremiumAdvertisement getPremiumAd(int premiumAdId) {
        return premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new IllegalArgumentException("Premium Advertisement not found with ID: " + premiumAdId));
    }

    public void updatePremiumAd(int premiumAdId, PremiumAdRequestDto premiumAdDto, MultipartFile img) {
        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new IllegalArgumentException("Premium Advertisement not found with ID: " + premiumAdId));

        // 광고 정보 업데이트
        premiumAd.setAdType(premiumAdDto.getAdType());
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot());
        premiumAd.setImage(imageService.saveImage(img)); // 이미지를 새로 저장
        premiumAd.setStartDate(premiumAdDto.getStartDate());
        premiumAd.setEndDate(premiumAdDto.getEndDate());

        premiumAdvertisementRepository.save(premiumAd);
    }

    public void deletePremiumAd(int premiumAdId) {
        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new IllegalArgumentException("Premium Advertisement not found with ID: " + premiumAdId));

        Advertisement ad = advertisementRepository.findById(premiumAd.getAdvertisement().getAdvertisementIndex())
                        .orElseThrow(() -> new IllegalArgumentException("Premium Advertisement not found with ID: " + premiumAdId));
        ad.setStatus(StatusType.DEL_WAIT);
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 삭제대기로 변경
    }

/*
    public List<LocalDate> getSoldOutDates() {
        return premiumAdvertisementRepository.findSoldOutDates();
    }*/

}
