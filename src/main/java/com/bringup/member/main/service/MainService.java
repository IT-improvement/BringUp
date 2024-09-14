package com.bringup.member.main.service;

import com.bringup.common.enums.StatusType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.repository.AdvertisementRepository;
import com.bringup.member.main.dto.UserAdvertisementResponseDto;
import com.bringup.member.main.dto.MemberInfoDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {
    private final UserRepository userRepository;

    private final AdvertisementRepository advertisementRepository;


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


    public List<UserAdvertisementResponseDto> getRandomAdvertisements(UserDetailsImpl userDetails) {
        // 모든 광고 리스트를 불러옴
        List<Advertisement> allAdvertisements = advertisementRepository.findAll();

        // ACTIVE 상태의 광고만 필터링 (반복문 사용)
        List<Advertisement> activeAdvertisements = new ArrayList<>();
        for (Advertisement ad : allAdvertisements) {
            if (ad.getStatus() == StatusType.ACTIVE) {
                activeAdvertisements.add(ad);
            }
        }

        // 랜덤으로 5개의 광고 선택
        Random random = new Random();
        List<UserAdvertisementResponseDto> randomAdvertisements = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();

        while (randomAdvertisements.size() < 5 && !activeAdvertisements.isEmpty()) {
            int randomIndex = random.nextInt(activeAdvertisements.size());
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
                Advertisement ad = activeAdvertisements.get(randomIndex);
                randomAdvertisements.add(
                        UserAdvertisementResponseDto.builder()
                                .advertisementImage(ad.getAdvertisementImage())  // 이미지 정보만 빌더에 추가
                                .build()
                );
            }
        }

        return randomAdvertisements;
    }

    private UserAdvertisementResponseDto convertToDto(Advertisement advertisement) {
        return UserAdvertisementResponseDto.builder()
                .advertisementIndex(advertisement.getAdvertisementIndex())
                .recruitmentIndex(advertisement.getRecruitmentIndex())
                .advertisementImage(advertisement.getAdvertisementImage())
                .type(advertisement.getType())
                .displayTime(advertisement.getDisplayTime())
                .status(advertisement.getStatus())
                .build();
    }
}


