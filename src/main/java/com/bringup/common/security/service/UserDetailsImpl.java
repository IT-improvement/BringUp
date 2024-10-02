package com.bringup.common.security.service;

import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String email;
    @JsonIgnore
    private String password;
    private List<GrantedAuthority> authorities;
    private String userType; // "COMPANY" or "USER"

    public static UserDetails fromCompany(Company company) {
        List<GrantedAuthority> authorities = company.getRole() != null ?
                List.of(new SimpleGrantedAuthority(company.getRole().name())) : null;

        return UserDetailsImpl.builder()
                .id(company.getCompanyId())
                .email(company.getManagerEmail())
                .password(company.getCompanyPassword())
                .authorities(authorities)
                .userType("COMPANY")
                .build();
    }

    public static UserDetails fromUser(UserEntity user) {
        List<GrantedAuthority> authorities = user.getRole() != null ?
                List.of(new SimpleGrantedAuthority(user.getRole().name())) : null;

        return UserDetailsImpl.builder()
                .id(user.getUserIndex())
                .email(user.getUserEmail())
                .password(user.getUserPassword())
                .authorities(authorities)
                .userType("USER")
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
