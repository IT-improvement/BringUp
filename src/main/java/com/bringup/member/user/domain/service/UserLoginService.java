package com.bringup.member.user.domain.service;

import com.bringup.common.security.jwt.JwtProvider;


import com.bringup.common.security.service.UserDetailsImpl;
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

    /*
    2. UserLoginService에서 인증 처리
    UserLoginService의 login 메서드는 UserLoginDTO로부터 사용자의 이메일과 비밀번호를 받아옵니다.
     */
    public UserLoginTokenDTO login(UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUserEmail(),
                userLoginDTO.getUserPassword()
        );/* 이메일과 비밀번호를 사용하여 UsernamePasswordAuthenticationToken 객체를 생성합니다.*/

        System.out.println("Service: login method called with " + userLoginDTO.getUserEmail());
        System.out.println("authenticationToken : " + authenticationToken);

        if (userLoginDTO == null) {
            throw new IllegalArgumentException("loginDto cannot be null");
        }

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);  /*
    AuthenticationManager를 통해 이 UsernamePasswordAuthenticationToken을 인증합니다.
    인증이 성공하면, SecurityContextHolder에 인증된 사용자의 정보가 저장됩니다.
         */


        /*
           3. 사용자 정보 추출 및 JWT 토큰 생성
         생성된 JWT 토큰은 UserLoginTokenDTO로 감싸져 반환됩니다.
         */
        // 수정된 부분: UserDetailsImpl 사용
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //  인증이 완료되면, Authentication 객체로부터 사용자 정보를 추출합니다. 여기서 CustomUserDetails 객체가 반환됩니다.
        System.out.println("userDetails : " + userDetails);

        String accessToken = jwtProvider.createAccessToken(userDetails);
        //  추출된 사용자 정보를 사용하여 JwtProvider의 createAccessToken 메서드를 호출하여 JWT 토큰을 생성합니다.
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

