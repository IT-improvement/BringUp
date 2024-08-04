package com.bringup.common.security.service;

import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("companyDetailsService")
@RequiredArgsConstructor
public class CompanyDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CompanyDetailsService: loadUserByUsername called with " + username);
        Company company = companyRepository.findByManagerEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return CompanyDetailsImpl.from(company);
    }
}