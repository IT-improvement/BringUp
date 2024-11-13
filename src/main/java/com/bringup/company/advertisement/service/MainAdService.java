package com.bringup.company.advertisement.service;

import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.entity.Payment;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.admin.payment.repository.PaymentRepository;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.MainAdRequestDto;
import com.bringup.company.advertisement.dto.request.MainDateRequestDto;
import com.bringup.company.advertisement.dto.response.UsableDisplayResponseDto;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.bringup.common.enums.AdvertisementErrorCode.ALREADY_ACTIVE;
import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class MainAdService {

    private final MainAdvertisementRepository mainAdvertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final ImageService imageService;
    private final ItemRepository itemRepository;
    private final PaymentRepository paymentRepository;

    // 메인 광고 생성
    public void createMainAd(MainAdRequestDto mainAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(mainAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        Payment order = paymentRepository.findByOrderIndex(mainAdDto.getOrderIdx())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitment(recruitment);
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisement.setStartDate(LocalDate.parse(mainAdDto.getStartDate()));
        advertisement.setOrder(order);
        advertisement.setEndDate(LocalDate.parse(mainAdDto.getEndDate()));

        advertisementRepository.save(advertisement);

        // 메인 광고 등록
        MainAdvertisement mainAd = new MainAdvertisement();
        mainAd.setAdvertisement(advertisement);
        mainAd.setMain_Image(imageService.saveImage(img));

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
        ad.setStringListFromList(mainAdDto.getUseDate());
        ad.setStatus(StatusType.CRT_WAIT); // 수정 시에도 초기 상태로 변경
        mainAd.getAdvertisement().setStartDate(LocalDate.parse(mainAdDto.getStartDate()));
        mainAd.getAdvertisement().setEndDate(LocalDate.parse(mainAdDto.getEndDate()));

        mainAdvertisementRepository.save(mainAd);
    }

    // 메인 광고 삭제
    public void deleteMainAd(int mainAdId) {
        MainAdvertisement mainAd = mainAdvertisementRepository.findById(mainAdId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Advertisement ad = mainAd.getAdvertisement();

        ad.setStatus(StatusType.DEL_WAIT); // 상태를 삭제 대기로 변경
        if(ad.getStatus().equals(StatusType.ACTIVE)){
            throw new AdvertisementException(ALREADY_ACTIVE);
        }
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

    public UsableDisplayResponseDto getUnavailableDates(MainDateRequestDto choicedatedto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // String으로 받은 날짜를 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(choicedatedto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(choicedatedto.getEndDate(), formatter);

        // 예약 불가능한 날짜를 저장할 Set (중복 방지)
        Set<LocalDate> unavailableDates = new HashSet<>();

        // 각 날짜별로 확인
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            String formattedDate = currentDate.format(formatter);

            // 해당 날짜의 메인 광고를 확인 (advertisement의 display_time에 해당 날짜가 포함되는지 확인)
            List<Advertisement> adsForDate = advertisementRepository.findAllByDisplayContaining(formattedDate);

            // 해당 날짜에 예약된 광고가 6개 이상일 경우 예약 불가능한 날짜로 추가
            if (adsForDate.size() >= 6) {
                unavailableDates.add(currentDate);
            }
        }

        // 불가능한 날짜 수 계산
        int nonDisplayCount = unavailableDates.size();

        // 전체 요청 일수 계산
        long requestedDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int availableDays = (int) (requestedDays - nonDisplayCount);

        Optional<Item> item = itemRepository.findByItemName("메인 광고 - " + availableDays + "일");

        return UsableDisplayResponseDto.builder()
                .nonDisplay(unavailableDates) // 예약 불가능한 날짜 수
                .itemIdx(item.get().getItemIndex())
                .itemName(item.get().getItemName())
                .itemPrice(item.get().getPrice())
                .build();
    }


    private MainAdResponseDto convertToDto(MainAdvertisement mainAd, Advertisement ad) {
        return MainAdResponseDto.builder()
                .mainId(mainAd.getMainId())
                .recruitmentIndex(ad.getRecruitment().getRecruitmentIndex())
                .startDate(mainAd.getAdvertisement().getStartDate())
                .endDate(mainAd.getAdvertisement().getEndDate())
                .discountRate(mainAd.getDiscountRate())
                .viewCount(ad.getV_count())
                .clickCount(ad.getC_count())
                .status(ad.getStatus())
                .build();
    }

    public int getItemPrice(int day){
        int itemIdx = 0;
        if(day == 1){
            itemIdx = 9;
        } else if (day == 3) {
            itemIdx = 10;
        } else {
            itemIdx = 11;
        }

        Item item = itemRepository.findByItemIndex(itemIdx)
                .orElseThrow(() -> new CompanyException(NOT_FOUND_ITEM));

        return item.getPrice();
    }
}
