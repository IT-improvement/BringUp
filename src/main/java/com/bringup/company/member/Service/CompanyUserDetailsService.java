package com.bringup.company.member.Service;

import com.bringup.company.member.DTO.request.CompanyDetails;
import com.bringup.company.member.Entity.Company;
import com.bringup.company.member.Repository.CompanyRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompanyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyUserDetailsService.class);

    private final CompanyRepository companyRepository;

    public CompanyUserDetailsService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String managerEmail) throws UsernameNotFoundException {
        logger.debug("Loading user by email: {}", managerEmail);

        Optional<Company> companyOptional = companyRepository.findByManagerEmail(managerEmail);
        Company company = companyOptional.orElseThrow(() -> new UsernameNotFoundException("Company not found with email: " + managerEmail));
        return new CompanyDetails(company);
    }
}
