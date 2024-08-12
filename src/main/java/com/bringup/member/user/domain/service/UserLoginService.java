package com.bringup.member.user.domain.service;

import com.bringup.common.security.jwt.JwtProvider;


import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.UserLoginDTO;
import com.bringup.member.user.dto.UserLoginTokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserLoginService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }
    /**
     * 로그인
     */
    public UserLoginTokenDTO login(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUserEmail(),
                userLoginDTO.getUserPassword()
        );

        System.out.println("Service: login method called with " + userLoginDTO.getUserEmail());
        System.out.println("authenticationToken : " + authenticationToken);

        if (userLoginDTO == null) {
            throw new IllegalArgumentException("loginDto cannot be null");
        }

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("userDetails : " + userDetails);

        String accessToken = jwtProvider.createAccessToken(userDetails);
        System.out.println("Service: JWT token created for " + userDetails.getUsername());

        return UserLoginTokenDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    /**
     * Email(userEmail) Check
     */
    public boolean checkEmail(String userEmail){
        System.out.println("Service: checkEmail method called with " + userEmail);
        return !userRepository.existsByUserEmail(userEmail);
    }



}
