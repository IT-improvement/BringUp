package com.bringup.company.advertisement.service;

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

    public List<AdvertisementResponseDto> getAdvertisements() {
        return advertisementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createAdvertisement(AdvertisementRequestDto requestDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setRecruitmentIndex(requestDto.getRecruitmentIndex());
        advertisement.setAdvertisementImage(requestDto.getAdvertisementImage());
        advertisement.setType(requestDto.getType());
        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("생성 대기");

        advertisementRepository.save(advertisement);

        // 어드민에게 승인 요청을 보냅니다. (예: 이메일, 알림 등)
        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void uploadAdvertisementImage(int recruitmentIndex, MultipartFile image) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Recruitment not found"));

        String imageUrl = saveImage(Math.toIntExact(recruitment.getCompany().getCompanyId()), recruitment.getCategory(), image);

        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setAdvertisementImage(imageUrl);
        advertisement.setStatus("이미지 수정 대기");

        advertisementRepository.save(advertisement);
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

    @Transactional
    public void updateAdvertisementType(AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setType(requestDto.getType());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void updateAdvertisementDisplayTime(AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setDisplayTime(requestDto.getDisplayTime());
        advertisement.setStatus("수정 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void extendAdvertisement(AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        // 광고 연장 로직 구현 (예: 현재 출력 시간에 추가 시간을 더하는 방식)
        advertisement.setStatus("연장 대기");

        advertisementRepository.save(advertisement);

        notifyAdminForApproval(advertisement);
    }

    @Transactional
    public void deleteAdvertisement(AdvertisementRequestDto requestDto) {
        Advertisement advertisement = advertisementRepository.findByRecruitmentIndex(requestDto.getRecruitmentIndex())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        advertisement.setStatus("삭제 대기");
        advertisementRepository.save(advertisement);

        notifyAdminForDeletionApproval(advertisement);
    }

    private void notifyAdminForDeletionApproval(Advertisement advertisement) {
        // 어드민에게 삭제 승인 요청을 보내는 로직을 작성합니다.
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
}