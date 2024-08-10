package com.bringup.member.user.domain.service;

import com.bringup.common.enums.RolesType;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 이메일을 가진 회원이 존재하는지 확인

        String email = joinDTO.getUserEmail();
        String password = joinDTO.getUserPassword();

        Boolean isExist = userRepository.existsByUserEmail(email);

        if (isExist) {

            return;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserEmail(joinDTO.getUserEmail());
        userEntity.setUserPassword(bCryptPasswordEncoder.encode(password)); //joinDTO의 데이터를 꺼내 암호화
        userEntity.setUserName(joinDTO.getUserName());
        userEntity.setUserAddress(joinDTO.getUserAddress());
        userEntity.setUserPhonenumber(joinDTO.getUserPhonenumber());
        userEntity.setUserBirthday(joinDTO.getUserBirthday());
        userEntity.setFreelancer(joinDTO.isFreelancer());
        userEntity.setStatus(joinDTO.getStatus());
        userEntity.setRole(RolesType.valueOf("ROLE_ADMIN"));

        userRepository.save(userEntity); // 디비에 저장
    }
}
