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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    @Value("${file.url}")
    private String fileUrl;

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
        List<Advertisement> allAdvertisements = advertisementRepository.findAll();
        List<Advertisement> activeAdvertisements = new ArrayList<>();
        for (Advertisement ad : allAdvertisements) {
            if (ad.getStatus() == StatusType.ACTIVE) {
                activeAdvertisements.add(ad);
            }
        }

        List<UserAdvertisementResponseDto> randomAdvertisements = new ArrayList<>();
        Random random = new Random();
        int maxAdvertisements = Math.min(5, activeAdvertisements.size());

        while (randomAdvertisements.size() < maxAdvertisements) {
            int randomIndex = random.nextInt(activeAdvertisements.size());
            Advertisement ad = activeAdvertisements.get(randomIndex);
            randomAdvertisements.add(convertToDto(ad));
        }

        return randomAdvertisements;
    }

    // Advertisement 엔티티를 UserAdvertisementResponseDto로 변환하는 메서드
    private UserAdvertisementResponseDto convertToDto(Advertisement advertisement) {
        String imageUrl = advertisement.getAdvertisementImage();

        // 이미지 경로가 '/'로 시작하지 않으면 '/'를 추가
        if (!imageUrl.startsWith("/")) {
            imageUrl = "/" + imageUrl;
        }

        return new UserAdvertisementResponseDto(
                advertisement.getAdvertisementIndex(),
                imageUrl,
                advertisement.getType(),
                advertisement.getDisplayTime()
        );
    }
}