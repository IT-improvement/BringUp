package com.bringup.common.config;

import com.bringup.member.user.jwt.JWTFilter;
import com.bringup.member.user.jwt.JWTUtil;
import com.bringup.member.user.jwt.LoginFilter;
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
@EnableWebSecurity //이클래스가 security config를 수정한다는 애너테이션

public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
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
                        .requestMatchers("/login", "/", "/join").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http //동작 순서는 상단부터 , 스프링 버전 3.1.x 일때는 무조건 람다 형식으로 메서드를 나열해야한다.
                .authorizeHttpRequests(auth -> auth //authorizeHttpRequests는 특정한 경로에 대해 요청을 허용학나 거부한다. 람다식 꼭사용해야함
                                .requestMatchers("/", "/phLogin", "/join", "/joinProc").permitAll() //특정한 경로에 요청을 진행할 수 있는 메서드
                                //permitAll : 모든 사용자에게 로그인을 하지않아도 접근 가능하게 해주는 메서드,
                                .requestMatchers("/admin").hasRole("ADMIN") //admin이면 접근가능
                                //hasRole : 특정한 규칙이 있어야 규칙이 있어야 경로에 접근가능
                                .requestMatchers("/member/**").hasAnyRole("ADMIN", "USER")
                                //hasAnyRole : 여러가지 규칙 설정가능
                                .anyRequest().authenticated()
                        //authenticated : 로그인만 진행되면 접근 가능
                        //denyAll : 로그인을 해도 접근못하게 하는 메서드
                )
                .formLogin(auth -> auth
                        .loginPage("/phLogin") //스프링 시큐리티가 자동으로 login으로 화면을 보여준다.
                        .loginProcessingUrl("/loginProc") //로그인페이지와 더불어서 loginProc 안에 프론트에서 넘긴 id 비밀번호를 시큐리티가 받아서 로그인 처리를 해준다.
                        .permitAll() //그 경로로 아무나 들어올수 있게 설정
                        .defaultSuccessUrl("/", true) // '/'로 설정하여 오류 해결
                );

        http
                .csrf(auth -> auth.disable());

        return http.build(); //받은 http를 build타입으로 반환
    }*/

