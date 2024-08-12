package com.bringup.common.security.service;

import com.bringup.company.user.Entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService: loadUserByUsername called with " + username);
        UserEntity user = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return CustomUserDetails.from(user);
    }
}
