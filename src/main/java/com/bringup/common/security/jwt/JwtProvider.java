package com.bringup.common.security.jwt;

import com.bringup.common.security.service.CompanyDetailsImpl;
import com.bringup.common.security.service.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
	private final String AUTHENTICATION_CLAIM_NAME = "roles";

	@Value("${jwt.secret-key}")
	private String accessSecret;

	@Value("${jwt.access-expiration-seconds}")
	private int accessExpirationSeconds;

	/**
	 * access 토큰 생성
	 */
	public String createAccessToken(CompanyDetailsImpl companyDetails) {
		Instant now = Instant.now();
		Date expiration = Date.from(now.plusSeconds(accessExpirationSeconds));
		SecretKey key = extractSecretKey();

		StringBuilder roles = new StringBuilder();
		// member roles 추출
		if(companyDetails.getAuthorities() != null && !companyDetails.getAuthorities().isEmpty()) {
			roles.append(
					companyDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(", "))
			);
		}

		return Jwts.builder()
			.claim("id", companyDetails.getId())
			.setSubject(companyDetails.getUsername())
			.setIssuedAt(Date.from(now))
			.setExpiration(expiration)
			.claim(AUTHENTICATION_CLAIM_NAME, roles.toString())
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	/**
	 * User access 토큰 생성
	 */

	public String createAccessToken(CustomUserDetails customUserDetails) {
		Instant now = Instant.now();
		Date expiration = Date.from(now.plusSeconds(accessExpirationSeconds));
		SecretKey key = extractSecretKey();

		String roles = customUserDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));

		return Jwts.builder()
				.claim("id", customUserDetails.getId())
				.setSubject(customUserDetails.getUsername())
				.setIssuedAt(Date.from(now))
				.setExpiration(expiration)
				.claim(AUTHENTICATION_CLAIM_NAME, roles)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}


	/**
	 * 권한 체크
	 */
	public Authentication toAuthentication(String token) {
		JwtParser jwtParser = Jwts.parserBuilder()
			.setSigningKey(extractSecretKey())
			.build();
		Claims claims = jwtParser.parseClaimsJws(token).getBody();

		Object roles = claims.get(AUTHENTICATION_CLAIM_NAME);
		List<GrantedAuthority> authorities = null;
		if(roles != null && !roles.toString().trim().isEmpty()) {
			authorities = List.of(new SimpleGrantedAuthority(roles.toString()));
		}

		UserDetails company = CompanyDetailsImpl.builder()
			.id(claims.get("id", Integer.class))
			.email(claims.getSubject())
			.password(null)
			.authorities(authorities)
			.build();

		return new UsernamePasswordAuthenticationToken(company, token, authorities);
	}

	/**
	 * 토큰 검증
	 */
	public void validate(String token) {
		JwtParser jwtParser = Jwts.parserBuilder()
			.setSigningKey(extractSecretKey())
			.build();

		jwtParser.parseClaimsJws(token);
	}

	/**
	 * SecretKey 추출
	 */
	private SecretKey extractSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
	}
}
