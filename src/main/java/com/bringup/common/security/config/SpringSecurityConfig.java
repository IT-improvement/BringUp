package com.bringup.common.security.config;

import com.bringup.common.exception.CustomAccessDeniedHandler;
import com.bringup.common.security.jwt.JwtFilter;
import com.bringup.common.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static com.bringup.common.enums.RolesType.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final UserDetailsService companyDetailsService;
    
    private final UserDetailsService customUserDetailsService;

    public SpringSecurityConfig(JwtProvider jwtProvider,
                                CustomAccessDeniedHandler customAccessDeniedHandler,
                                @Qualifier("companyDetailsService") UserDetailsService companyDetailsService,
                                @Qualifier("customUserDetailsService") UserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.companyDetailsService = companyDetailsService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider companyAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(companyDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider customAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(companyAuthenticationProvider(), customAuthenticationProvider()));
    }

    /**
     * public http
     */
    @Bean
    @Order(1)
    public SecurityFilterChain permitAllFilterChain(HttpSecurity http) throws Exception {
        httpSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(permitAllRequestMatchers()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitAllRequestMatchers()).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    /**
     * 토큰 인증 및 권한이 필요한 http
     */
    @Bean
    @Order(2)
    public SecurityFilterChain authenticatedFilterChain(HttpSecurity http) throws Exception {
        httpSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(AuthRequestMatchers()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AuthRequestMatchers())
                        .hasAnyAuthority(ROLE_MEMBER.name(), ROLE_ADMIN.name(), ROLE_COMPANY.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(new JwtFilter(jwtProvider), ExceptionTranslationFilter.class);
        return http.build();
    }

    @Bean    // no @Order defaults to last
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        httpSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(OPTIONS, "/**")
                );

        return http.build();
    }

    /**
     * permitAll endpoint
     */
    private RequestMatcher[] permitAllRequestMatchers() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(POST, "/com/login"),            // 로그인
                antMatcher(POST, "/com/join/first"),       // 회원가입 1단계
                antMatcher(POST, "/com/join/second"),      // 회원가입 2단계
                antMatcher(POST, "/com/checkId"),          // ID 중복 체크
                antMatcher(GET, "/WEB-INF/views/**"),          // 웹 리소스
                antMatcher(GET, "/resources/**"),
                antMatcher(GET, "/company/page/**"),              // 기업 페이지
                antMatcher("/member/**"),
                antMatcher("/github/**")// 정적 리소스
        );

        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    /**
     * JWT Authentication, Roles Authorization endpoint
     */
    private RequestMatcher[] AuthRequestMatchers() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(POST, "/com/companyName"),               // 기업명 헤더 삽입
                antMatcher(PUT, "/com/user"),                       // 회원 정보 수정
                antMatcher(DELETE, "/com/user"),                    // 회원 탈퇴
                antMatcher(GET, "/com/companyInfo/post"),           // 회원 정보 조회

                antMatcher(POST, "/com/recruitment/register"),       // 채용 등록
                antMatcher(POST, "/com/recruitment/update/{recruitmentId}"), // 채용 수정
                antMatcher(POST, "/com/recruitment/delete/{recruitmentId}"), // 채용 삭제
                antMatcher(GET, "/com/recruitment/list"),           // 채용 리스트 조회

                antMatcher(POST, "/com/c_reviews"),                 // 기업 리뷰 열람
                antMatcher(POST, "/com/c_review/delete"),           // 기업 리뷰 삭제

                antMatcher(POST, "/com/i_reviews"),                 // 면접 리뷰 열람
                antMatcher(POST, "/com/i_review/delete")            // 면접 리뷰 삭제
        );

        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private void httpSecuritySetting(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // form 기반 로그인을 사용하지 않음.
                .httpBasic(AbstractHttpConfigurer::disable) // 기본으로 제공하는 http 사용하지 않음
                .rememberMe(AbstractHttpConfigurer::disable) // 토큰 기반이므로 세션 기반의 인증 사용하지 않음
                .headers(headers -> headers.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable)) // x-Frame-Options 헤더 비활성화, 클릭재킹 공격 관련
                .logout(AbstractHttpConfigurer::disable) // stateful 하지 않기때문에 필요하지 않음
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 생성을 하지 않음
                .anonymous(AbstractHttpConfigurer::disable); // 익명 사용자 접근 제한, 모든 요청이 인증 필요
    }
}