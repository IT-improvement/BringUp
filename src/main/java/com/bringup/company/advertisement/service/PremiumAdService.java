package com.bringup.company.advertisement.service;

import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.entity.Payment;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.admin.payment.repository.PaymentRepository;
import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.ChoicedateRequestDto;
import com.bringup.company.advertisement.dto.request.PremiumAdRequestDto;
import com.bringup.company.advertisement.dto.response.PremiumAdResponseDto;
import com.bringup.company.advertisement.dto.response.UsableDisplayResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.PremiumAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.exception.CompanyException;
import com.bringup.company.user.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.bringup.common.enums.AdvertisementErrorCode.ALREADY_ACTIVE;
import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class PremiumAdService {

    private final ImageService imageService;
    private final PremiumAdvertisementRepository premiumAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository companyRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ItemRepository itemRepository;
    private final PaymentRepository paymentRepository;

    /*public List<String> getUnavailableDates(ChoicedateRequestDto choicedatedto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // String으로 받은 날짜를 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(choicedatedto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(choicedatedto.getEndDate(), formatter);

        // 예약 불가능한 날짜 및 시간대를 저장할 리스트
        List<String> unavailableDates = new ArrayList<>();

        // 각 날짜별로 확인
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            String formattedDate = currentDate.format(formatter);

            // 해당 날짜의 프리미엄 광고를 확인 (advertisement의 display_time에 해당 날짜가 포함되는지 확인)
            List<Advertisement> adsForDate = advertisementRepository.findAllByDisplayContaining(formattedDate);

            if (!adsForDate.isEmpty()) {
                for (Advertisement ad : adsForDate) {
                    // 각 광고의 프리미엄 타임슬롯 확인 (advertisement_premium 테이블에서 확인)
                    List<PremiumAdvertisement> premiumAds = premiumAdvertisementRepository.findByAdvertisement(ad);

                    String[] timeSlots = {
                            "01:00 ~ 04:00",
                            "04:00 ~ 07:00",
                            "07:00 ~ 10:00",
                            "10:00 ~ 13:00",
                            "13:00 ~ 16:00",
                            "16:00 ~ 19:00",
                            "19:00 ~ 22:00",
                            "22:00 ~ 01:00"
                    };

                    // 예약된 타임슬롯을 저장
                    List<String> reservedTimeSlots = new ArrayList<>();

                    for (String timeSlot : timeSlots) {
                        boolean isTimeSlotUnavailable = premiumAds.stream()
                                .anyMatch(premiumAd -> premiumAd.getTimeSlot().equals(timeSlot));

                        // 예약된 시간대에 추가
                        if (isTimeSlotUnavailable) {
                            reservedTimeSlots.add(timeSlot);
                        }
                    }

                    // 예약된 타임슬롯을 모두 표시
                    if (!reservedTimeSlots.isEmpty()) {
                        for (String reservedSlot : reservedTimeSlots) {
                            unavailableDates.add(formattedDate + " (" + reservedSlot + ")");
                        }
                    }
                }
            }
        }

        return unavailableDates;  // 예약 불가능한 날짜 및 시간대 반환
    }*/

    public UsableDisplayResponseDto getAdvertisementData(ChoicedateRequestDto choicedatedto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 날짜 범위를 미리 파싱
        LocalDate startDate = LocalDate.parse(choicedatedto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(choicedatedto.getEndDate(), formatter);
        String requestedTimeSlot = choicedatedto.getTimeSlot();

        String adType = findAdTypeByTimeSlot(requestedTimeSlot);

        // 범위 내 모든 날짜에 대해 광고 데이터를 한 번에 조회
        Set<LocalDate> unavailableDates = new HashSet<>();

        // 각 날짜에 대해 displayTime에 포함 여부 확인
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            String formattedDate = currentDate.format(formatter);

            // 해당 날짜를 포함하는 광고가 있는지 확인
            List<Advertisement> adsForDate = advertisementRepository.findAllByDisplayContaining(formattedDate);
            boolean isUnavailable = adsForDate.stream()
                    .anyMatch(ad -> premiumAdvertisementRepository.findByAdvertisement(ad).stream()
                            .anyMatch(premiumAd -> premiumAd.getTimeSlot().equals(requestedTimeSlot)));

            // 예약이 되어 있다면 날짜를 예약 불가능 목록에 추가
            if (isUnavailable) {
                unavailableDates.add(currentDate);
            }
        }

        // 예약 불가능한 날짜 수 계산 및 가용 일수 확인
        int nonDisplayCount = unavailableDates.size();
        int availableDays = 3 - nonDisplayCount;

        Optional<Item> item;
        if (availableDays == 1) {
            item = itemRepository.findByItemName(adType + " - 1day");
        } else if (availableDays == 2) {
            item = itemRepository.findByItemName(adType + " - 2day");
        } else if (availableDays == 0) {
            return UsableDisplayResponseDto.builder()
                    .nonDisplay(unavailableDates)
                    .itemIdx(0)
                    .build();
        } else {
            item = itemRepository.findByItemName(adType);
        }

        return UsableDisplayResponseDto.builder()
                .nonDisplay(unavailableDates)
                .itemIdx(item.get().getItemIndex())
                .itemName(item.get().getItemName())
                .itemPrice(item.get().getPrice())
                .build();
    }

    private String findAdTypeByTimeSlot(String requestedTimeSlot) {
        // 시간대와 광고 타입을 매핑한 Map 생성
        Map<String, String> timeSlotToAdType = Map.of(
                "01:00 ~ 04:00", "P3",
                "04:00 ~ 07:00", "P1",
                "07:00 ~ 10:00", "GP",
                "10:00 ~ 13:00", "P2",
                "13:00 ~ 16:00", "P1",
                "16:00 ~ 19:00", "GP",
                "19:00 ~ 22:00", "P1",
                "22:00 ~ 01:00", "P3"
        );

        // 요청된 시간대에 맞는 광고 타입 반환, 없을 경우 null 반환
        return timeSlotToAdType.getOrDefault(requestedTimeSlot, null);
    }

    public void createPremiumAd(PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        Payment order = paymentRepository.findByOrderIndex(premiumAdDto.getOrderIdx())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitment(recruitment);
        advertisement.setStringListFromList(premiumAdDto.getDisplayDate());
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setStatus(StatusType.ACTIVE); // 초기 상태
        advertisement.setOrder(order);
        advertisement.setStartDate(LocalDate.parse(premiumAdDto.getStartDate()));
        advertisement.setEndDate(LocalDate.parse(premiumAdDto.getEndDate()));
        advertisementRepository.save(advertisement);

        // 프리미엄 광고 정보 등록
        PremiumAdvertisement premiumAd = new PremiumAdvertisement();
        premiumAd.setAdvertisement(advertisement);
        premiumAd.setAdType(premiumAdDto.getAdType()); // enum 사용
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot());
        premiumAd.setImage(imageService.saveImage(img));

        premiumAdvertisementRepository.save(premiumAd);
    }

    public void updatePremiumAd(int premiumAdId, PremiumAdRequestDto premiumAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(premiumAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = advertisementRepository.findById(premiumAd.getAdvertisement().getAdvertisementIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        // 광고 정보 업데이트
        ad.setStatus(StatusType.CRT_WAIT);
        ad.setStringListFromList(premiumAdDto.getDisplayDate());
        premiumAd.setAdType(premiumAdDto.getAdType());
        premiumAd.setTimeSlot(premiumAdDto.getTimeSlot());
        premiumAd.setImage(imageService.saveImage(img)); // 이미지를 새로 저장
        premiumAd.getAdvertisement().setStartDate(LocalDate.parse(premiumAdDto.getStartDate()));
        premiumAd.getAdvertisement().setEndDate(LocalDate.parse(premiumAdDto.getEndDate()));

        premiumAdvertisementRepository.save(premiumAd);
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 CRT_WAIT으로 생성
    }

    public void deletePremiumAd(int premiumAdId) {
        PremiumAdvertisement premiumAd = premiumAdvertisementRepository.findById(premiumAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        premiumAd.getAdvertisement().setStatus(StatusType.DEL_WAIT);
        if(premiumAd.getAdvertisement().getStatus().equals(StatusType.ACTIVE)){
            throw new AdvertisementException(ALREADY_ACTIVE);
        }
        // TODO: 웹소켓으로 어드민소통해야함 일단 그냥 삭제대기로 변경
        premiumAdvertisementRepository.save(premiumAd);
    }

    public PremiumAdResponseDto getPremiumAdDetail(UserDetailsImpl userDetails, Integer adIdx) {
        Advertisement ad = advertisementRepository.findByAdvertisementIndex(adIdx)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        PremiumAdvertisement premiumAdvertisement = premiumAdvertisementRepository.findById(ad.getPremiumAdvertisement().getPremiumIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CompanyException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        return convertToDto(ad, premiumAdvertisement);
    }

    private PremiumAdResponseDto convertToDto(Advertisement ad, PremiumAdvertisement pad) {
        return PremiumAdResponseDto.builder()
                .advertisementIndex(ad.getAdvertisementIndex())
                .recruitmentIndex(ad.getRecruitment().getRecruitmentIndex())
                .status(ad.getStatus())
                .adType(pad.getAdType())
                .timeSlot(pad.getTimeSlot())
                .startDate(pad.getAdvertisement().getStartDate())
                .endDate(pad.getAdvertisement().getEndDate())
                .ad_img(pad.getImage())
                .view_count(ad.getV_count())
                .click_count(ad.getC_count())
                .build();
    }
/*
    public List<LocalDate> getSoldOutDates() {
        return premiumAdvertisementRepository.findSoldOutDates();
    }*/

    // 날짜 리스트를 콤마로 구분된 문자열로 변환
/*
    private String convertDatesToString(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dateList = startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());

        // 날짜 리스트를 "yyyy-MM-dd" 형식의 문자열로 변환
        return dateList.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(", "));
    }
*/

}
