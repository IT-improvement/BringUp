package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.AnnouncementAdRequestDto;
import com.bringup.company.advertisement.dto.response.AnnouncementAdResponseDto;
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

import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class AnnouncementAdService {

    private final AnnouncementAdvertisementRepository announcementAdvertisementRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;

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
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);

        AnnouncementAdvertisement announcementAd = new AnnouncementAdvertisement();
        announcementAd.setAdvertisement(advertisement);
        announcementAd.setDurationMonths(announcementAdDto.getDurationMonths());
        announcementAd.setStartDate(announcementAdDto.getStartDate());
        announcementAd.setEndDate(announcementAdDto.getStartDate().plusMonths(announcementAdDto.getDurationMonths()));

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
        announcementAd.setDurationMonths(announcementAdDto.getDurationMonths());
        announcementAd.setStartDate(announcementAdDto.getStartDate());
        announcementAd.setEndDate(announcementAdDto.getStartDate().plusMonths(announcementAdDto.getDurationMonths()));

        announcementAdvertisementRepository.save(announcementAd);
    }

    @Transactional
    public void deleteAnnouncementAd(int announcementId) {
        AnnouncementAdvertisement announcementAd = announcementAdvertisementRepository.findById(announcementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        announcementAd.getAdvertisement().setStatus(StatusType.DEL_WAIT);
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
                .announcementId(announcementAd.getAnnouncementId())
                .durationMonths(announcementAd.getDurationMonths())
                .startDate(announcementAd.getStartDate())
                .endDate(announcementAd.getEndDate())
                .status(announcementAd.getAdvertisement().getStatus())
                .build();
    }
}
