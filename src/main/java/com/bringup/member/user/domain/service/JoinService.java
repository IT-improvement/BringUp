package com.bringup.member.user.domain.service;

import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 이메일을 가진 회원이 존재하는지 확인
        if (userRepository.findByUserEmail(joinDTO.getUserEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(joinDTO.getUserEmail());
        userEntity.setUserPassword(bCryptPasswordEncoder.encode(joinDTO.getUserPassword()));
        userEntity.setUserName(joinDTO.getUserName());
        userEntity.setUserAddress(joinDTO.getUserAddress());
        userEntity.setUserPhoneNumber(joinDTO.getUserPhoneNumber());
        userEntity.setUserBirthday(joinDTO.getUserBirthday());
        userEntity.setFreelancer(joinDTO.isFreelancer());
        userEntity.setStatus(joinDTO.getStatus());

        userRepository.save(userEntity);
    }
}
