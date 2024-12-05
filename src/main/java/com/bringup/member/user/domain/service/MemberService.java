package com.bringup.member.user.domain.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.entity.MilitaryEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.MilitaryRepsitory;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import com.bringup.member.user.dto.MemberUpdateDto;
import com.bringup.member.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_EMAIL;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
//@Log4j
public class MemberService{
    private final UserRepository userRepository;
    private final JoinService joinService;
    private final UserLoginService userLoginService;
    private final PasswordEncoder passwordEncoder;
    private final MilitaryRepsitory militaryRepository;


    @Transactional
    public void updateMember(UserDetailsImpl userDetails, MemberUpdateDto dto){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        user.setUserEmail(dto.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        user.setUserName(dto.getUserName());
        user.setUserAddress(dto.getUserAddress());
        user.setUserBirthday(dto.getUserBirthday());
        user.setFreelancer(dto.isFreelancer());

        userRepository.save(user);
    }

    @Transactional
    public void deleteMember(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));
        user.setStatus("INACTIVE");
    }

    public UserEntity getMemberInfo(UserDetailsImpl userDetails){
        return userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_EMAIL));
    }


    public UserResponseDTO getUserAndMilitaryInfo(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER_EMAIL));

        MilitaryEntity military = militaryRepository.findByUserIndex(user.getUserIndex())
                .orElse(null);

        return new UserResponseDTO(user, military);
    }



    public String getUserName(UserDetailsImpl userDetails) {
        Optional<UserEntity> userOptional = userRepository.findByUserEmail(userDetails.getUsername());

        if (userOptional.isPresent()) {
            return userOptional.get().getUserName();
        } else {
            throw new IllegalArgumentException("User not found for email: " + userDetails.getUsername());
        }
    }

    public String getUserEmail(UserDetailsImpl userDetails) {
        Optional<UserEntity> userOptional = userRepository.findByUserEmail(userDetails.getUsername());

        if (userOptional.isPresent()) {
            return userOptional.get().getUserEmail();
        } else {
            throw new IllegalArgumentException("User not found for email: " + userDetails.getUsername());
        }
    }
}
