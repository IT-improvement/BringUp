package com.bringup.common.security.service;

import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BringUserDetailsService implements UserDetailsService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("BringUserDetailsService: loadUserByUsername called with " + username);

        // 먼저 기업 사용자 검색
        Company company = companyRepository.findByManagerEmail(username).orElse(null);
        if (company != null) {
            return UserDetailsImpl.fromCompany(company);
        }

        UserEntity user = userRepository.findByUserEmail(username).orElse(null);
        if (user != null) {
            return UserDetailsImpl.fromUser(user);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
