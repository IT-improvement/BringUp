package com.bringup.member.user.domain.service;

import com.bringup.common.enums.RolesType;
import com.bringup.member.portfolio.letter.domain.LetterEntity;
import com.bringup.member.portfolio.letter.domain.LetterRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LetterRepository letterRepository;
    @Autowired
    public JoinService(UserRepository userRepository, PasswordEncoder passwordEncoder, LetterRepository letterRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.letterRepository = letterRepository;
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
        userEntity.setRole(RolesType.ROLE_MEMBER);

        userRepository.save(userEntity);
        Optional<UserEntity> user = userRepository.findByUserEmail(email);
        int userIndex = user.get().getUserIndex();
        LetterEntity letterEntity = new LetterEntity(userIndex);
        letterRepository.save(letterEntity);

    }

    /**
     * ID(userEmail) Check
     */
    public boolean checkId(String userEmail){
        return !userRepository.findByUserEmail(userEmail).isPresent();
    }
}