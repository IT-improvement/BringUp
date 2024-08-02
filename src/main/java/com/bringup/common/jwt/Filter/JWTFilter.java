package com.bringup.common.jwt.Filter;

import com.bringup.common.jwt.JWTUtil;
import com.bringup.company.member.DTO.request.CompanyDetails;
import com.bringup.company.member.Entity.Company;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //userEntity를 생성하여 값 set
        Company companyEntity = new Company();
        companyEntity.setCompanyName(username);
        companyEntity.setCompanyPassword("temppassword");
        companyEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CompanyDetails customUserDetails = new CompanyDetails(companyEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 디버깅 로그 추가
        System.out.println("JWTFilter: Starting filter for request " + request.getRequestURI());

        String authorization = request.getHeader("Authorization");
        System.out.println("JWTFilter: Authorization header: " + authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("JWTFilter: No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        System.out.println("JWTFilter: Extracted JWT token: " + token);

        if (jwtUtil.isExpired(token)) {
            System.out.println("JWTFilter: JWT token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        System.out.println("JWTFilter: JWT token is valid. Username: " + username + ", Role: " + role);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Company company = new Company();
            company.setManagerEmail(username);
            company.setRole(role);

            CompanyDetails companyDetails = new CompanyDetails(company);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(companyDetails, null, companyDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("JWTFilter: User authenticated and set in SecurityContext");
        } else {
            System.out.println("JWTFilter: User already authenticated or username is null");
        }

        filterChain.doFilter(request, response);
        System.out.println("JWTFilter: Finished filtering for request " + request.getRequestURI());
    }
}
