package com.bringup.member.user.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private SecretKey secretKey;
    //프로퍼티 암호키 가져와 저장
    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {


        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    //검증할 3개의 메서드
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userEmail", String.class); //parser을 이용해 jwt내부 데이터 리턴해준다.
        //verifyWith(secretKey) 우리서버에서 키가 생성되었는지 검증하는 메서드 그뒤로 build()타입으로 리턴
        //parseSignedClaims(token).
        //payload로 .get("username", String.class);  스트링타입 데이터를 가져옴
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) { //시간이 만료되었는지 확인하는 메서드

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        //getExpiration().before(new Date());로 시간값을 검증한다.
    }


    //토큰 생성 메서드 Long expiredMs = 만료시간
    public String createJwt(String userEmail, String role, Long expiredMs) { //메서드가 호출 될때 아이디, 권한, 만료시간을 인자로 호출 된다

        return Jwts.builder() //토큰 만들기
                .claim("userEmail", userEmail) //.claum을 통해 내부에 데이터를 넣어줄수있다.
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //소멸시간 설정 현재 발행시간 + 토큰 유지시간
                .signWith(secretKey) // 암호화 진행
                .compact(); //토큰을 생성
    }
}