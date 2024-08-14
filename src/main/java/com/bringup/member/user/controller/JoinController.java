package com.bringup.member.user.controller;

import com.bringup.common.response.BfResponse;
import com.bringup.member.user.domain.service.JoinService;
import com.bringup.member.user.domain.service.UserLoginService;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/member")
public class JoinController {

    private final JoinService joinService;



    @Autowired
    public JoinController(JoinService joinService, UserLoginService userLoginService) {
        this.joinService = joinService;
        this.userLoginService = userLoginService;
    }
    @GetMapping("/join")
    public String joinProcess() {
        return "member/user/userJoin"; // 회원가입 완료 후 리다이렉트할 페이지
    }

    @PostMapping("/joinProc")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) { //JoinDTO를 @RequestBody로 받지 않으면, Spring은 form-data 또는 query parameters 방식으로 데이터를 받아야 합니다. 하지만 Postman에서 raw 타입으로 JSON 데이터를 전송할 경우, 이 데이터는 @RequestBody로 받아야 제대로 매핑됩니다.
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }
    @PostMapping("/checkId")
    public ResponseEntity<BfResponse<?>> checkId(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("user_Email");
        boolean isAvailable = joinService.checkId(userEmail);
        return ResponseEntity.ok(new BfResponse<>(isAvailable));
    }
}
