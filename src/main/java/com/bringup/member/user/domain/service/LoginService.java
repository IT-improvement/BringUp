package com.bringup.member.user.domain.service;

import com.bringup.common.security.jwt.JwtProvider;
import com.bringup.company.user.DTO.request.LoginDto;
import com.bringup.company.user.DTO.response.LoginTokenDto;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


public class LoginService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public LoginService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }
    /**
     * 로그인
     */
    public LoginTokenDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUserEmail(),
                loginDto.getUserPassword()
        );

        System.out.println("Service: login method called with " + loginDto.getUserEmail());
        System.out.println("authenticationToken : " + authenticationToken);

        if (loginDto == null) {
            throw new IllegalArgumentException("loginDto cannot be null");
        }

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("userDetails : " + userDetails);

        String accessToken = jwtProvider.createAccessToken(userDetails);
        System.out.println("Service: JWT token created for " + userDetails.getUsername());

        return LoginTokenDto.builder()
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
