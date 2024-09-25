package com.bringup.member.user.controller;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.user.domain.service.JoinService;
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
    public JoinController(JoinService joinService) {
        this.joinService = joinService;

    }
    @GetMapping("/join")
    public String joinProcess() {
        return "member/user/userJoin"; // 회원가입 완료 후 리다이렉트할 페이지
    }

    @PostMapping("/joinProc")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.joinProcess(joinDTO);
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            // 잘못된 입력 데이터로 인한 오류 처리
            System.err.println("회원가입 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("회원가입 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).body("회원가입 중 오류가 발생했습니다.");
        }
    }


    @PostMapping("/checkId")
    public ResponseEntity<ResponseDto> checkId(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        boolean isAvailable = joinService.checkId(userEmail);

        // 사용 가능 여부에 따라 응답 메시지를 설정
        String message = isAvailable ? ResponseMessage.EMAIL_AVAILABLE + " 사용 가능 여부: " + isAvailable
                : ResponseMessage.EMAIL_TAKEN + " 사용 가능 여부: " + isAvailable;

        return ResponseEntity.ok(new ResponseDto(ResponseCode.SUCCESS, message));
    }


}
