package com.bringup.company.advertisement.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.dto.response.AdvertisementResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.exception.AdvertisementException;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bringup.common.enums.AdvertisementErrorCode.*;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ImageService imageService;

    public List<AdvertisementResponseDto> getAdvertisements(UserDetailsImpl userDetails) {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        List<AdvertisementResponseDto> responseDtos = new ArrayList<>();

        for (Advertisement ad : advertisements) {
            Recruitment recruitment = recruitmentRepository.findById(ad.getRecruitmentIndex())
                    .orElse(null);
            if (recruitment != null && Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
                responseDtos.add(convertToDto(ad));
            }
        }
        return responseDtos;
    }

    @Transactional
    public void createAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        String imageUrl = imageService.upLoadImage(image);

        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setType(requestDto.getType());
        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus(StatusType.CRT_WAIT);

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void uploadAdvertisementImage(UserDetailsImpl userDetails, int recruitmentIndex, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        String imageUrl = imageService.upLoadImage(image);

        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setStatus(StatusType.CRT_WAIT);

        advertisementRepository.save(advertisement);
    }

    @Transactional
    public void updateAdvertisementType(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        advertisement.setType(requestDto.getType());
        advertisement.setStatus(StatusType.CRT_WAIT);

        advertisementRepository.save(advertisement);
        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void updateAdvertisementDisplayTime(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus(StatusType.CRT_WAIT);

        advertisementRepository.save(advertisement);
        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void extendAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        advertisement.setStatus(StatusType.CRT_WAIT);
        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void deleteAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        advertisement.setStatus(StatusType.DEL_WAIT);
        advertisementRepository.save(advertisement);

        notifyAdminForDeletionApproval(advertisement);
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void updateAdvertisementStatus() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Advertisement advertisement : advertisements) {
            LocalDate displayDate = LocalDate.parse(advertisement.getDisplayTime(), formatter);
            if (!displayDate.isAfter(today) && !StatusType.INACTIVE.equals(advertisement.getStatus())) {
                advertisement.setStatus(StatusType.DEL_WAIT);
                advertisementRepository.save(advertisement);
            }
        }
    }

    public AdvertisementResponseDto getAdvertisementDetail(UserDetailsImpl userDetails, Integer advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        return convertToDto(advertisement);
    }

    private AdvertisementResponseDto convertToDto(Advertisement advertisement) {
        return AdvertisementResponseDto.builder()
                .advertisementIndex(advertisement.getAdvertisementIndex())
                .recruitmentIndex(advertisement.getRecruitmentIndex())
                .advertisementImage(advertisement.getAdvertisementImage())
                .type(advertisement.getType())
                .displayTime(advertisement.getDisplayTime())
                .status(advertisement.getStatus())
                .build();
    }

    /*private String saveAdvertisementImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        try {
            // 이미지 저장 경로 지정
            String uploadDir = "src/main/resources/static/advertisement/";
            String fileName = "Advertisement_" + UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }*/

    private void notifyAdminForApproval(Advertisement advertisement) {
        // 어드민에게 승인 요청을 보내는 로직을 작성합니다.
    }

    private void notifyAdminForDeletionApproval(Advertisement advertisement) {
        // 어드민에게 삭제 승인 요청을 보내는 로직을 작성합니다.
    }
}