package com.bringup.member.user.domain.service;

import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Optional을 사용하여 UserEntity를 조회
        Optional<UserEntity> userDataOptional = userRepository.findByUserEmail(userEmail);

        // UserEntity가 존재하면 CustomUserDetails로 변환하여 반환, 그렇지 않으면 예외 발생
        UserEntity userData = userDataOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        return new CustomUserDetails(userData);
    }
}
