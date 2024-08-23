package com.bringup.common.security.jwt;

import com.bringup.common.security.service.UserDetailsImpl;
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
	private final String USER_TYPE_CLAIM_NAME = "userType";

	@Value("${jwt.secret-key}")
	private String accessSecret;

	@Value("${jwt.access-expiration-seconds}")
	private int accessExpirationSeconds;

	/**
	 * access 토큰 생성
	 */
	public String createAccessToken(UserDetailsImpl userDetails) {
		Instant now = Instant.now();
		Date expiration = Date.from(now.plusSeconds(accessExpirationSeconds));
		SecretKey key = extractSecretKey();

		String roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));

		return Jwts.builder()
				.claim("id", userDetails.getId())
				.setSubject(userDetails.getUsername())
				.setIssuedAt(Date.from(now))
				.setExpiration(expiration)
				.claim(AUTHENTICATION_CLAIM_NAME, roles)
				.claim(USER_TYPE_CLAIM_NAME, userDetails.getUserType())
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
		if (roles != null && !roles.toString().trim().isEmpty()) {
			authorities = List.of(new SimpleGrantedAuthority(roles.toString()));
		}

		String userType = claims.get(USER_TYPE_CLAIM_NAME, String.class);

		UserDetailsImpl userDetails = UserDetailsImpl.builder()
				.id(claims.get("id", Integer.class))
				.email(claims.getSubject())
				.password(null)
				.authorities(authorities)
				.userType(userType)
				.build();

		return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
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
