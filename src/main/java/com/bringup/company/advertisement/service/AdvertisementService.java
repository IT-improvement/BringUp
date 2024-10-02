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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
                    .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));
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

        advertisement.setRecruitmentIndex(requestDto.getRecruitmentIndex());
        //advertisement.setAdvertisementImage(imageUrl);
        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus(StatusType.CRT_WAIT);

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    /*@Transactional
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
    }*/

    @Transactional
    public void deleteAdvertisement(UserDetailsImpl userDetails, Integer ad_index, String reason) {
        Advertisement advertisement = advertisementRepository.findByAdvertisementIndex(ad_index)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        advertisement.setStatus(StatusType.DEL_WAIT);
        advertisementRepository.save(advertisement);

        notifyAdminForDeletionApproval(advertisement, reason);
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

    @Transactional
    public void updateAdvertisement(UserDetailsImpl userDetails, Integer advertisementId, MultipartFile img, Integer date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Advertisement advertisement = advertisementRepository.findByAdvertisementIndex(advertisementId)
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_ADVERTISEMENT));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new AdvertisementException(NOT_FOUND_RECRUITMENT));

        // 사용자가 해당 광고에 접근할 권한이 있는지 확인
        if (!Objects.equals(recruitment.getCompany().getCompanyId(), userDetails.getId())) {
            throw new AdvertisementException(UNAUTHORIZED_ADVERTISEMENT_ACCESS);
        }

        // img와 date가 모두 null이면 예외 처리
        if (img.isEmpty() && date == null) {
            throw new AdvertisementException(NOT_FOUND_IMG_AND_DATE);
        }

        // date가 존재하면 기존 날짜에 더함
        if (date != null) {
            LocalDate existingDate = LocalDate.parse(advertisement.getDisplayTime(), formatter);
            LocalDate newDate = existingDate.plusDays(date);
            advertisement.setDisplayTime(newDate.toString());
        }

        // img가 존재하면 이미지를 저장
        if (!img.isEmpty()) {
            //advertisement.setAdvertisementImage(imageService.saveImage(img));
        }

        // 광고 수정 내용을 저장
        advertisementRepository.save(advertisement);

        /**
         * TODO : 어드민 완성시 어드민에 승인요청 보내는걸로 변경할거임
         */
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

    private AdvertisementResponseDto convertToDto(Advertisement advertisement) {
        return AdvertisementResponseDto.builder()
                .advertisementIndex(advertisement.getAdvertisementIndex())
                .recruitmentIndex(advertisement.getRecruitmentIndex())
                //.advertisementImage(advertisement.getAdvertisementImage())
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

    private void notifyAdminForDeletionApproval(Advertisement advertisement, String reason) {
        // 어드민에게 삭제 승인 요청을 보내는 로직을 작성합니다.
    }
}