package com.bringup.common.security.service;

import com.bringup.company.user.Entity.Company;
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
@AllArgsConstructor @NoArgsConstructor
public class CompanyDetailsImpl implements UserDetails {
	private Long id;
	private String email;
	@JsonIgnore
	private String password;
	private List<GrantedAuthority> authorities;

	public static UserDetails from(Company company) {
		List<GrantedAuthority> authorities = company.getRole() != null ?
				List.of(new SimpleGrantedAuthority(company.getRole().name())): null;

		return new CompanyDetailsImpl(
				company.getCompanyId(),
				company.getManagerEmail(),
				company.getCompanyPassword(),
				authorities
		);
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