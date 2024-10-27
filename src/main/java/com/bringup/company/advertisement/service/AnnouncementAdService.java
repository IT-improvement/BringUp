package com.bringup.company.advertisement.service;

import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.AnnouncementAdRequestDto;
import com.bringup.company.advertisement.dto.request.DateRequestDto;
import com.bringup.company.advertisement.dto.response.AnnouncementAdResponseDto;
import com.bringup.company.advertisement.dto.response.ItemInfoResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.AnnouncementAdvertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.AnnouncementAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.exception.CompanyException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static com.bringup.common.enums.AdvertisementErrorCode.ALREADY_ACTIVE;
import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class AnnouncementAdService {

    private final AnnouncementAdvertisementRepository announcementAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ItemRepository itemRepository;

    public ItemInfoResponseDto getAnnounceAdPrice(DateRequestDto dto){
        String itemName = "공고 광고 - " + dto.getDisplayTime() + "달";

        Item item = itemRepository.findByItemName(itemName)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        return ItemInfoResponseDto.builder()
                .itemName(item.getItemName())
                .itemIdx(item.getItemIndex())
                .itemPrice(item.getPrice())
                .build();
    }

    @Transactional
    public void createAnnouncementAd(AnnouncementAdRequestDto announcementAdDto, UserDetailsImpl userDetails) {
        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(announcementAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        // 광고 등록
        Advertisement advertisement = new Advertisement();
        advertisement.getRecruitment().setRecruitmentIndex(announcementAdDto.getRecruitmentIndex());
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setDisplay(String.valueOf(announcementAdDto.getDurationDays()));
        advertisement.setStartDate(announcementAdDto.getStartDate());
        advertisement.setEndDate(announcementAdDto.getEndDate());
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);

        AnnouncementAdvertisement announcementAd = new AnnouncementAdvertisement();
        announcementAd.setAdvertisement(advertisement);
        announcementAdvertisementRepository.save(announcementAd);
    }

    @Transactional
    public void updateAnnouncementAd(int announcementId, AnnouncementAdRequestDto announcementAdDto, UserDetailsImpl userDetails) {
        AnnouncementAdvertisement announcementAd = announcementAdvertisementRepository.findById(announcementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!announcementAd.getAdvertisement().getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        announcementAd.getAdvertisement().setStatus(StatusType.CRT_WAIT);
        announcementAd.getAdvertisement().setStartDate(announcementAdDto.getStartDate());
        announcementAd.getAdvertisement().setEndDate(announcementAdDto.getEndDate());

        announcementAdvertisementRepository.save(announcementAd);
    }

    @Transactional
    public void deleteAnnouncementAd(int announcementId) {
        AnnouncementAdvertisement announcementAd = announcementAdvertisementRepository.findById(announcementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        announcementAd.getAdvertisement().setStatus(StatusType.DEL_WAIT);
        if(announcementAd.getAdvertisement().getStatus().equals(StatusType.ACTIVE)){
            throw new AdvertisementException(ALREADY_ACTIVE);
        }
        announcementAdvertisementRepository.save(announcementAd);
    }

    public AnnouncementAdResponseDto getAnnouncementAdDetail(int announcementId, UserDetailsImpl userDetails) {
        AnnouncementAdvertisement announcementAd = announcementAdvertisementRepository.findById(announcementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!announcementAd.getAdvertisement().getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        return convertToDto(announcementAd);
    }

    private AnnouncementAdResponseDto convertToDto(AnnouncementAdvertisement announcementAd) {
        return AnnouncementAdResponseDto.builder()
                .announcementId(announcementAd.getAnnouncementId())
                .recruitmentIndex(announcementAd.getAdvertisement().getRecruitment().getRecruitmentIndex())
                .startDate(announcementAd.getAdvertisement().getStartDate())
                .endDate(announcementAd.getAdvertisement().getEndDate())
                .status(announcementAd.getAdvertisement().getStatus())
                .build();
    }
}
