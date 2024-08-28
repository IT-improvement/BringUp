package com.bringup.company.advertisement.service;

import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.dto.request.AdvertisementRequestDto;
import com.bringup.company.advertisement.dto.response.AdvertisementResponseDto;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ImageService imageService;

    public List<AdvertisementResponseDto> getAdvertisements(UserDetailsImpl userDetails) {
        return advertisementRepository.findAll().stream()
                .filter(ad -> recruitmentRepository.findById(ad.getRecruitmentIndex())
                        .map(recruitment -> (recruitment.getCompany().getCompanyId() == (userDetails.getId())))
                        .orElse(false))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to create an advertisement for this recruitment.");
        }

        String imageUrl = imageService.upLoadImage(image);

        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitmentIndex(requestDto.getRecruitmentIndex());
        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setType(requestDto.getType());
        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("생성 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void uploadAdvertisementImage(UserDetailsImpl userDetails, int recruitmentIndex, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to upload an image for this recruitment.");
        }

        String imageUrl = imageService.upLoadImage(image);

        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setStatus("이미지 수정 대기");

        advertisementRepository.save(advertisement);
    }

    @Transactional
    public void updateAdvertisementType(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to update the type of this advertisement.");
        }

        advertisement.setType(requestDto.getType());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void updateAdvertisementDisplayTime(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to update the display time of this advertisement.");
        }

        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void extendAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to extend this advertisement.");
        }

        advertisement.setStatus("연장 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void deleteAdvertisement(UserDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to delete this advertisement.");
        }

        advertisement.setStatus("삭제 대기");
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
            if (!displayDate.isAfter(today) && !"삭제".equals(advertisement.getStatus())) {
                advertisement.setStatus("삭제");
                advertisementRepository.save(advertisement);
            }
        }
    }

    public AdvertisementResponseDto getAdvertisementDetail(UserDetailsImpl userDetails, Integer advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId() == (userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to view this advertisement.");
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