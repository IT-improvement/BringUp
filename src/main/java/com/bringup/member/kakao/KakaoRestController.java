package com.bringup.member.kakao;

import com.bringup.member.kakao.dto.KakaoUserInfoResponseDto;
import com.bringup.member.kakao.dto.KakaoUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
public class KakaoRestController {

    @Value("${kakaoapi.key}")
    private String key;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoService kakaoService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/login")
    public Map<String, String> kakaoLogin() {
        System.out.println("test");
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + key + "&redirect_uri=" + redirectUri;

        // JSON 형식으로 응답을 반환하기 위해 Map을 사용
        Map<String, String> response = new HashMap<>();
        response.put("location", location);

        return response; // JSON으로 반환
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callBack(@RequestParam("code") String code){
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        ResponseEntity<? super KakaoUserResponseDto> response = kakaoUserService.signUpByKakao(userInfo);

        String htmlResponse = "<html><body><script type=\"text/javascript\">window.close();</script></body></html>";

        String responseCode  = response.getStatusCode().toString();

        if(responseCode.equals("EE"))
            return null;
        else
            return null;
    }
}
