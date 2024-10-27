package com.bringup.company.advertisement.service;

import com.bringup.admin.payment.entity.Item;
import com.bringup.admin.payment.repository.ItemRepository;
import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.BannerAdRequestDto;
import com.bringup.company.advertisement.dto.request.DateRequestDto;
import com.bringup.company.advertisement.dto.response.BannerAdResponseDto;
import com.bringup.company.advertisement.dto.response.ItemInfoResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.BannerAdvertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.advertisement.repository.BannerAdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import com.bringup.company.user.exception.CompanyException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.bringup.common.enums.AdvertisementErrorCode.ALREADY_ACTIVE;
import static com.bringup.common.enums.AdvertisementErrorCode.NOT_FOUND_ADVERTISEMENT;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_RECRUITMENT;

@Service
@RequiredArgsConstructor
public class BannerAdService {
    private final BannerAdvertisementRepository bannerAdRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ImageService imageService;
    private final ItemRepository itemRepository;

    public ItemInfoResponseDto getBannerInfo(DateRequestDto dto){
        String itemName = "배너 광고 - " + dto.getDisplayTime() + "일";

        Item item = itemRepository.findByItemName(itemName)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        return ItemInfoResponseDto.builder()
                .itemIdx(item.getItemIndex())
                .itemPrice(item.getPrice())
                .itemName(item.getItemName())
                .build();
    }

    @Transactional
    public void createBannerAd(BannerAdRequestDto bannerAdDto, MultipartFile img, UserDetailsImpl userDetails) {

        Recruitment recruitment = recruitmentRepository.findByRecruitmentIndex(bannerAdDto.getRecruitmentIndex())
                .orElseThrow(() -> new CompanyException(NOT_FOUND_RECRUITMENT));

        if(!recruitment.getCompany().getCompanyId().equals(userDetails.getId())){
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        Advertisement advertisement = new Advertisement();
        advertisement.getRecruitment().setRecruitmentIndex(bannerAdDto.getRecruitmentIndex());
        advertisement.setV_count(0); // 초기 조회 수
        advertisement.setC_count(0); // 초기 클릭 수
        advertisement.setDisplay(String.valueOf(bannerAdDto.getExposureDays()));
        advertisement.setStartDate(bannerAdDto.getStartDate());
        advertisement.setEndDate(bannerAdDto.getEndDate());
        advertisement.setStatus(StatusType.CRT_WAIT); // 초기 상태
        advertisementRepository.save(advertisement);


        BannerAdvertisement bannerAd = new BannerAdvertisement();
        bannerAd.setAdvertisement(advertisement);
        bannerAd.setBannerImage(imageService.saveImage(img));

        bannerAdRepository.save(bannerAd);
    }

    @Transactional
    public BannerAdResponseDto getBannerAdDetail(int bannerId, UserDetailsImpl userDetails) {
        BannerAdvertisement bannerAd = bannerAdRepository.findById(bannerId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        // 광고 소유자인지 확인
        if (!bannerAd.getAdvertisement().getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        return convertToDto(bannerAd);
    }

    @Transactional
    public void updateBannerAd(int bannerId, BannerAdRequestDto bannerAdDto, MultipartFile img, UserDetailsImpl userDetails) {
        BannerAdvertisement bannerAd = bannerAdRepository.findById(bannerId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        if (!bannerAd.getAdvertisement().getRecruitment().getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new CompanyException(NOT_FOUND_MEMBER_ID);
        }

        bannerAd.getAdvertisement().setStatus(StatusType.CRT_WAIT);
        bannerAd.setBannerImage(imageService.saveImage(img));
        bannerAd.getAdvertisement().setStartDate(bannerAdDto.getStartDate());
        bannerAd.getAdvertisement().setEndDate(bannerAdDto.getEndDate());

        bannerAdRepository.save(bannerAd);
    }

    @Transactional
    public void deleteBannerAd(int bannerId) {
        BannerAdvertisement bannerAd = bannerAdRepository.findById(bannerId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        bannerAd.getAdvertisement().setStatus(StatusType.DEL_WAIT);
        if(bannerAd.getAdvertisement().getStatus().equals(StatusType.ACTIVE)){
            throw new AdvertisementException(ALREADY_ACTIVE);
        }
        bannerAdRepository.save(bannerAd);
    }

    private BannerAdResponseDto convertToDto(BannerAdvertisement bannerAd) {
        return BannerAdResponseDto.builder()
                .bannerAdIndex(bannerAd.getBannerId())
                .recruitmentIndex(bannerAd.getAdvertisement().getRecruitment().getRecruitmentIndex())
                .startDate(bannerAd.getAdvertisement().getStartDate())
                .endDate(bannerAd.getAdvertisement().getEndDate())
                .viewCount(bannerAd.getAdvertisement().getV_count())
                .clickCount(bannerAd.getAdvertisement().getC_count())
                .status(bannerAd.getAdvertisement().getStatus())
                .build();
    }
}
