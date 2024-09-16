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


    // 광고 목록 중 ACTIVE 상태인 광고를 랜덤으로 최대 5개 가져오는 메서드
    public List<UserAdvertisementResponseDto> getRandomActiveAdvertisements() {
        // 모든 광고를 불러옴
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
        int maxAdvertisements = Math.min(5, activeAdvertisements.size());
        List<Integer> selectedIndexes = new ArrayList<>();

        while (randomAdvertisements.size() < maxAdvertisements) {
            int randomIndex = random.nextInt(activeAdvertisements.size());
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
                Advertisement ad = activeAdvertisements.get(randomIndex);
                randomAdvertisements.add(convertToDto(ad));
            }
        }

        return randomAdvertisements;
    }

    // Advertisement 엔티티를 AdvertisementResponseDto로 변환하는 메서드
    private UserAdvertisementResponseDto convertToDto(Advertisement advertisement) {
        return new UserAdvertisementResponseDto(
                advertisement.getAdvertisementIndex(),
                advertisement.getAdvertisementImage(),
                advertisement.getType(),
                advertisement.getDisplayTime()
        );
    }
}