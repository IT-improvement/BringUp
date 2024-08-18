package com.bringup.admin.user.Service;

import com.bringup.admin.user.DTO.request.AdminCreationRequest;
import com.bringup.admin.user.DTO.request.AdminLoginDto;
import com.bringup.admin.user.DTO.response.AdminLoginTokenDto;
import com.bringup.common.enums.RolesType;
import com.bringup.common.security.jwt.JwtProvider;
import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserEntity createAdminAccount(AdminCreationRequest adminCreationRequest) {
        UserEntity adminUser = new UserEntity();
        adminUser.setUserEmail(adminCreationRequest.getUserEmail());
        adminUser.setUserPassword(passwordEncoder.encode(adminCreationRequest.getUserPassword())); // 비밀번호 암호화
        adminUser.setUserName(adminCreationRequest.getUserName());
        adminUser.setUserPhonenumber(adminCreationRequest.getUserPhonenumber());
        adminUser.setRole(RolesType.ROLE_ADMIN); // 어드민 역할 부여

        return userRepository.save(adminUser);
    }

    public AdminLoginTokenDto login(AdminLoginDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUserEmail(), loginRequestDto.getUserPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        CompanyDetailsImpl userDetails = (CompanyDetailsImpl) authentication.getPrincipal();

        String token = jwtProvider.createAccessToken(userDetails);

        return AdminLoginTokenDto.builder()
                .accessToken(token)
                .build();
    }
}