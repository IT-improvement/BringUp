package com.bringup.member.kakao;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.kakao.dto.KakaoUserInfoResponseDto;
import com.bringup.member.kakao.dto.KakaoUserResponseDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUserService {


    private final UserRepository userRepository;

    public ResponseEntity<? super KakaoUserResponseDto> signUpByKakao(KakaoUserInfoResponseDto user){

        String email = user.getKakaoAccount().getEmail();
        String name = user.getKakaoAccount().getName();
        String phoneNumber = user.getKakaoAccount().getPhoneNumber();
        String brithDay = user.getKakaoAccount().getBirthDay();

        boolean existByEmail = userRepository.existsByUserEmail(email);
        if(existByEmail)
            return KakaoUserResponseDto.existedEmail();

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUserEmail(email);
        joinDTO.setUserName(name);
        joinDTO.setUserBirthday(brithDay);

        UserEntity userEntity = new UserEntity(joinDTO);

        try {
            userRepository.save(userEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return KakaoUserResponseDto.success();
    }
}
