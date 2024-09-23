package com.bringup.member.main.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.main.dto.CompanyImageDto;
import com.bringup.member.main.dto.UserAdvertisementResponseDto;
import com.bringup.member.main.dto.MemberInfoDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository  companyRepository;


    public MemberInfoDto getMemberInfo(UserDetailsImpl userDetails) {
        // userDetails에서 이메일을 가져와서 해당 이메일로 유저 정보를 조회
        UserEntity userEntity = userRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + userDetails.getUsername()));

        // 조회한 유저 정보를 DTO로 변환
        return MemberInfoDto.builder()
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .build();
    }

    // 광고 목록 중 ACTIVE 상태인 광고를 랜덤으로 최대 5개 가져오는 메서드
    public List<UserAdvertisementResponseDto> getRandomActiveAdvertisements() {
        // 모든 광고를 불러옵니다.
        List<Advertisement> allAdvertisements = advertisementRepository.findAll();

        // ACTIVE 상태인 광고만 필터링
        List<Advertisement> activeAdvertisements = new ArrayList<>();
        for (Advertisement ad : allAdvertisements) {
            if (ad.getStatus() == StatusType.ACTIVE) {
                activeAdvertisements.add(ad);
            }
        }

        // 최대 5개의 랜덤 광고를 선택
        List<UserAdvertisementResponseDto> randomAdvertisements = new ArrayList<>();
        Random random = new Random();
        int maxAdvertisements = Math.min(2, activeAdvertisements.size());

        while (randomAdvertisements.size() < maxAdvertisements) {
            int randomIndex = random.nextInt(activeAdvertisements.size());
            Advertisement ad = activeAdvertisements.get(randomIndex);

            // DTO 변환 및 이미지 경로 설정
            randomAdvertisements.add(convertToDto(ad));
        }

        return randomAdvertisements;
    }


    public List<CompanyImageDto> getActiveCompanyImages() {
        List<Company> activeCompanies = companyRepository.findAllByStatus(StatusType.ACTIVE);

        if (activeCompanies.isEmpty()) {
            return new ArrayList<>(); // 회사 목록이 비어 있을 경우 빈 리스트 반환
        }

        List<CompanyImageDto> companyImageList = new ArrayList<>();
        Random random = new Random();

        // 최대 6개의 랜덤한 회사 선택 (단, 회사 수가 6개 미만일 경우 그만큼만 선택)
        int maxCompanies = Math.min(6, activeCompanies.size());
        Set<Integer> selectedIndices = new HashSet<>(); // 중복 방지를 위한 인덱스 저장

        while (selectedIndices.size() < maxCompanies) {
            int randomIndex = random.nextInt(activeCompanies.size());
            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Company company = activeCompanies.get(randomIndex);

                // companyImg에서 첫 번째 이미지 URL만 가져오기
                String companyImg = company.getCompanyImg();
                if (companyImg.contains(",")) {
                    companyImg = companyImg.split(",")[0]; // 첫 번째 이미지 URL만 사용
                }

                CompanyImageDto dto = new CompanyImageDto(
                        company.getCompanyId(),
                        companyImg // 첫 번째 이미지 URL만 설정
                );
                companyImageList.add(dto);
            }
        }

        return companyImageList;
    }

    private UserAdvertisementResponseDto convertToDto(Advertisement advertisement) {


        return new UserAdvertisementResponseDto(
                advertisement.getAdvertisementIndex(),
                advertisement.getAdvertisementImage(),
                advertisement.getType(),
                advertisement.getDisplayTime()
        );
    }
}
