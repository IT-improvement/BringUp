package com.bringup.common.security.config;

import com.bringup.common.exception.CustomAccessDeniedHandler;
import com.bringup.common.security.jwt.JwtFilter;
import com.bringup.common.security.jwt.JwtProvider;
import com.bringup.common.security.service.BringUserDetailsService;
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
    private final BringUserDetailsService bringUserDetailsService;

    public SpringSecurityConfig(JwtProvider jwtProvider,
                                CustomAccessDeniedHandler customAccessDeniedHandler,
                                BringUserDetailsService bringUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.bringUserDetailsService = bringUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(bringUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider()));
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
                        //.requestMatchers("/company/**").hasAuthority(ROLE_COMPANY.name()) // ROLE이 Company인것만 /company 에 접근가능
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
                // 웹 리소스
                antMatcher(POST, "/com/login"),            // 로그인
                antMatcher(POST, "/com/join/first"),       // 회원가입 1단계
                antMatcher(POST, "/com/join/second"),      // 회원가입 2단계
                antMatcher(POST, "/com/checkId"),          // ID 중복 체크
                antMatcher(GET, "/WEB-INF/views/**"),          // 웹 리소스
                antMatcher(GET, "/resources/**"),
                antMatcher(GET, "/company/**"),              // 기업 페이지
                antMatcher("/"),
/*
                antMatcher("/member/**"),
*/
                antMatcher("/ws/**"),
              /*  antMatcher("/github/**"),// 정적 리소스*/
                antMatcher("/member/potofolio"),
                antMatcher("/member/letter"),//이력서
                antMatcher("/member/resume"),//자소서
                antMatcher("/member/companyReview"),
                antMatcher("/member/interviewReview"),
                antMatcher("/member/notice"),
                antMatcher("/member/createNotice"),
                antMatcher("/member/userNotice"),
                antMatcher("/member/noticeDetail"),
                antMatcher("/member/notice/noticeDetail/{boardIndex}"),
                antMatcher("/member/Login"),
                antMatcher("/member/userLogin"),
                antMatcher("/member/recruitmentPage"),
                antMatcher("/member/topRecruitment"),
                antMatcher("/member/confirmRecruitment"),
                antMatcher("/member/AnnouncementRecruitment"),
                antMatcher("/member/proposeRecruitment"),
                antMatcher("/member/visitRecruitment"),
                antMatcher("/member/myReview"),
                antMatcher("/image/**"),
                antMatcher("/recruitment/detail/{recruitmentIndex}"),
                antMatcher("/main/image/**"),
                antMatcher("/main/advertisements"),
                antMatcher("/main/recruitmentImage"),
                antMatcher("/api/**"),
                antMatcher("/login/**"),
                antMatcher("/main/premium"),
                antMatcher("/main/main"),
                antMatcher("/main/banner"),
                antMatcher("/member/m_reviews"),
                antMatcher("/member/company"),
                antMatcher("/page/admin/**"),
                antMatcher("/member/join"),

                antMatcher("/member/bookmark"),

                antMatcher("/member/joinProc"),
                antMatcher("/main/list"),
                antMatcher("/member/memberProfile"),
                antMatcher("/member/interview/iv_list"),
                antMatcher("/member/m_reviewDetail"),
                antMatcher("/member/createReview"),
                antMatcher("/member/editReview"),
                antMatcher("/member/reviewDetail/{reviewId}"),
                antMatcher("/member/myCareer"),
                antMatcher("/freelancer/**"),
                antMatcher("/openai/**"),
                antMatcher("/member/git"),
                antMatcher("/member/notion"),
                antMatcher("/member/checkId"),
                antMatcher("/member/blog"),
                antMatcher("/member/file"),
                antMatcher("/member/letterWrite"),
                antMatcher("/member/blogList"),
                antMatcher("/member/record"),
                antMatcher("/member/awards"),
                antMatcher("/member/career"),

                antMatcher("/award/delete/{index}"),
                antMatcher("/portfolio/blog/delete"),
                antMatcher("/portfolio/career/delete"),

                antMatcher("/school/delete/{index}"),

                antMatcher("/mem/certificate/delete/{index}"),


                antMatcher("/freelancer/**")


        );

        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    /**
     * JWT Authentication, Roles Authorization endpoint
     */
    private RequestMatcher[] AuthRequestMatchers() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(POST, "/com/companyName"),               // 기업명 헤더 삽입
                antMatcher(GET, "/com/companyInfo/post"),           // 회원 정보 조회
                antMatcher("/com/user/**"),

                antMatcher("/com/recruitment/**"),
                antMatcher("/com/bookmarkCount"),

                antMatcher(POST, "/com/c_reviews"),                 // 기업 리뷰 열람
                antMatcher(POST, "/com/c_review/delete"),           // 기업 리뷰 삭제

                antMatcher(POST, "/com/i_reviews"),                 // 면접 리뷰 열람
                antMatcher(POST, "/com/i_review/delete"),         // 면접 리뷰 삭제
                antMatcher("/com/advertisement/**"),
                antMatcher("/com/freelancer/**"),
                antMatcher("/com/headhunt/**"),
                antMatcher("/com/volunteers"),
                antMatcher("/membership/**"), // 사용자 멤버십
                antMatcher("/member/name"),

                antMatcher("/member/userMain"),
                antMatcher("/main/memberInfo"),
                antMatcher("/member/{reviewId}"),
                antMatcher("/member/delete/{reviewId}"),
                antMatcher(GET, "/member/info"),
                antMatcher("/member/m_create"),
                antMatcher("/member/userNotice"),
                antMatcher("/member/notice/createPost"),
                antMatcher("/member/notice/detail/list"),
                antMatcher("/member/notice/postDetail/{boardIndex}"),
                antMatcher("/admin/**"),
                antMatcher(GET, "/member/applyList"),

                antMatcher(GET, "/mem/addCompany/list"),
                antMatcher("/mem/addCompany/{company_index}"),
                antMatcher(POST,"/mem/applyRecruitment"),

                antMatcher("/member/interview/{reviewId}"),
                antMatcher("/member/interview/delete/{reviewId}"),
                antMatcher("/member/interview/iv_create"),
                antMatcher("/mem/**"),

                antMatcher("/portfolio/blog/list"),
                antMatcher("/portfolio/blog/insert"),

                antMatcher("/portfolio/letter/info"),
                antMatcher("/portfolio/letter/insert"),

                antMatcher("/github/**"),
                antMatcher("/school/info/list"),
                antMatcher("/school/insert"),


                antMatcher("/award/insert"),
                antMatcher("/award/list"),
                antMatcher("/portfolio/career/list"),
                antMatcher("/portfolio/career/insert"),
                antMatcher("/mem/certificate/list"),


                antMatcher("/recruitment/scrap/{recruitmentIndex}"),
                antMatcher("/recruitment/scrap/delete/{recruitmentIndex}"),

                antMatcher("/cv/insert"),




                antMatcher("/recruitment/isBookmarked/{recruitmentIndex}"),
                antMatcher("/recruitment/visit/{recruitmentIndex}"),
                antMatcher("/recruitment/visitList"),



                antMatcher("/member/reviewDetail/{reviewId}")
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