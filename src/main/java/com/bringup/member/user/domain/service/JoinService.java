package com.bringup.member.user.domain.service;

import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String email = joinDTO.getUserEmail();
        String password = joinDTO.getUserPassword();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Boolean isExist = userRepository.existsByUserEmail(email);

        if (isExist) {
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(joinDTO.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(password));
        userEntity.setUserName(joinDTO.getUserName());
        userEntity.setUserAddress(joinDTO.getUserAddress());
        userEntity.setUserPhonenumber(joinDTO.getUserPhonenumber());
        userEntity.setUserBirthday(joinDTO.getUserBirthday());
        userEntity.setFreelancer(joinDTO.isFreelancer());
        userEntity.setStatus(joinDTO.getStatus());
        userEntity.setRole("ROLE_ADMIN");

        userRepository.save(userEntity);
    }
}