package com.bringup.common.jwt;

import com.bringup.company.member.DTO.request.CompanyDetails;
import com.bringup.company.member.Service.CompanyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CompanyLoginFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CompanyUserDetailsService companyUserDetailsService;

    public CompanyLoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CompanyUserDetailsService companyUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.companyUserDetailsService = companyUserDetailsService;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("userid");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userEmail = obtainUsername(request);
        String password = obtainPassword(request);

        logger.debug("Attempting authentication for user: {}", userEmail);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CompanyDetails customUserDetails = (CompanyDetails) authentication.getPrincipal();

        String userEmail = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userEmail, role, 60 * 60 * 10L);
        response.addHeader("Authorization", "Bearer " + token);

        logger.debug("Successfully authenticated user: {}", userEmail);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
        logger.debug("Failed to authenticate user: {}", obtainUsername(request));
    }
}
