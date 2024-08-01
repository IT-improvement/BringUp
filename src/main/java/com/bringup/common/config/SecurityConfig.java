package com.bringup.common.config;

import com.bringup.common.jwt.JWTFilter;
import com.bringup.common.jwt.JWTUtil;
import com.bringup.common.jwt.MemberLoginFilter;
import com.bringup.common.jwt.CompanyLoginFilter;
import com.bringup.company.member.Service.CompanyUserDetailsService;
import com.bringup.member.user.domain.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService memberUserDetailsService;
    private final CompanyUserDetailsService companyUserDetailsService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomUserDetailsService memberUserDetailsService, CompanyUserDetailsService companyUserDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.memberUserDetailsService = memberUserDetailsService;
        this.companyUserDetailsService = companyUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/login", "/company/login", "/", "/join").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new MemberLoginFilter(authenticationManager(), jwtUtil, memberUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new CompanyLoginFilter(authenticationManager(), jwtUtil, companyUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
