package com.bringup.member.user.domain.service;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import com.bringup.member.user.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//@Log4j
public class MemberService implements UserDetailsService{
    private final UserRepository userRepository;
    private final JoinService joinService;
    private final UserLoginService userLoginService;

    public int updateMember(MemberUpdateDto memberUpdateDto){
//        //Optional 오류 (이유 모름)
//        UserEntity userEntity = userRepository.findByUserEmail(memberUpdateDto.getUserEmail());
//        userEntity.updateUserEmail(memberUpdateDto.getUserEmail());
//        userEntity.updateUserName(memberUpdateDto.getUserName());
//        userEntity.updatePassword(memberUpdateDto.getUserPassword());
//        userEntity.updateUserAddress(memberUpdateDto.getUserAddress());
//        userEntity.updateUserBirthday(memberUpdateDto.getUserBirthday());
//        userEntity.updateUserPhoneNumber(memberUpdateDto.getUserPhoneNumber());
//        userEntity.updateStatus(memberUpdateDto.getStatus());
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodePW = encoder.encode(memberUpdateDto.getUserPassword());
//        userEntity.updatePassword(encodePW);
//        userRepository.save(userEntity);
//        return userEntity.getUserIndex();
        return 0;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
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
