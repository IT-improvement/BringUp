package com.bringup.member.main.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.main.domain.MainService;
import com.bringup.member.user.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private MainService mainService;

    @PostMapping("/memberInfo")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userName = mainService.getUserName(userDetails);
        String userEmail = mainService.getUserEmail(userDetails);

        // 이름과 이메일을 Map으로 반환 list를 왜 안썼나
        //**List**는 순서가 있는 데이터 구조로, 여러 개의 값을 저장하지만 각 값의 의미를 명시적으로 나타내지 않습니다.
        //**Map**은 키-값 쌍으로 데이터를 관리하며, 각각의 데이터가 어떤 의미를 가지는지 명확하게 표현할 수 있습니다.
        //사용자 이름과 이메일처럼 서로 다른 종류의 데이터를 함께 반환할 때는, Map을 사용하면 가독성과 명확성이 높아지기 때문에 더 적합합니다.
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("name", userName);
        userInfo.put("email", userEmail);

        return ResponseEntity.ok(new BfResponse<>(SUCCESS, userInfo));
    }

}
