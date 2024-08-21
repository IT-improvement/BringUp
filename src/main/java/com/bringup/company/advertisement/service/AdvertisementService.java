package com.bringup.company.advertisement.service;

import com.bringup.common.security.service.CompanyDetailsImpl;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;

    public List<AdvertisementResponseDto> getAdvertisements(CompanyDetailsImpl userDetails) {
        return advertisementRepository.findAll().stream()
                .filter(ad -> recruitmentRepository.findById(ad.getRecruitmentIndex())
                        .map(recruitment -> (recruitment.getCompany().getCompanyId()==(userDetails.getId())))
                        .orElse(false))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createAdvertisement(CompanyDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Recruitment recruitment = recruitmentRepository.findById(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId()==(userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to create an advertisement for this recruitment.");
        }

        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitmentIndex(requestDto.getRecruitmentIndex());
        advertisement.setAdvertisementImage(requestDto.getAdvertisementImage());
        advertisement.setType(requestDto.getType());
        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("생성 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void uploadAdvertisementImage(CompanyDetailsImpl userDetails, int recruitmentIndex, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!(recruitment.getCompany().getCompanyId()==(userDetails.getId()))) {
            throw new RuntimeException("You do not have permission to upload an image for this recruitment.");
        }

        String imageUrl = saveImage(Math.toIntExact(recruitment.getCompany().getCompanyId()), recruitment.getCategory(), image);

        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setStatus("이미지 수정 대기");

        advertisementRepository.save(advertisement);
    }

    @Transactional
    public void updateAdvertisementType(CompanyDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RuntimeException("You do not have permission to update the type of this advertisement.");
        }

        advertisement.setType(requestDto.getType());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void updateAdvertisementDisplayTime(CompanyDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RuntimeException("You do not have permission to update the display time of this advertisement.");
        }

        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void extendAdvertisement(CompanyDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
            throw new RuntimeException("You do not have permission to extend this advertisement.");
        }

        advertisement.setStatus("연장 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void deleteAdvertisement(CompanyDetailsImpl userDetails, AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
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

    public AdvertisementResponseDto getAdvertisementDetail(CompanyDetailsImpl userDetails, Integer advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        Recruitment recruitment = recruitmentRepository.findById(advertisement.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        if (!recruitment.getCompany().getCompanyId().equals(userDetails.getId())) {
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

    private String saveImage(int companyIndex, String recruitmentTitle, MultipartFile image) {
        // 현재 시간 가져오기
        String currentTimestamp = String.valueOf(System.currentTimeMillis());

        // 파일 이름 생성
        String fileName = companyIndex + "_" + recruitmentTitle + "_" + currentTimestamp + "_" + image.getOriginalFilename();

        // 저장 경로 설정
        String uploadDir = "/path/to/upload/directory"; // 실제 저장 경로로 변경
        File uploadPath = new File(uploadDir);

        // 경로가 없으면 폴더 생성
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // 파일 저장
        File saveFile = new File(uploadPath, fileName);
        try {
            image.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }

        // 이미지 URL 생성 (예: http://localhost:8080/images/)
        return "http://localhost:8080/images/" + fileName;
    }

    private void notifyAdminForApproval(Advertisement advertisement) {
        // 어드민에게 승인 요청을 보내는 로직을 작성합니다.
    }

    private void notifyAdminForDeletionApproval(Advertisement advertisement) {
        // 어드민에게 삭제 승인 요청을 보내는 로직을 작성합니다.
    }
}