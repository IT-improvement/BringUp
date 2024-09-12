package com.bringup.member.main.domain;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainService {
    private final UserRepository userRepository;

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
