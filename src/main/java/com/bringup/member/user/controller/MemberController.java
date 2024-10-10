package com.bringup.member.user.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.domain.service.JoinService;
import com.bringup.member.user.domain.service.MemberService;
import com.bringup.member.user.domain.service.UserLoginService;
import com.bringup.member.user.dto.JoinDTO;
import com.bringup.member.user.dto.MemberUpdateDto;
import com.bringup.member.user.dto.UserLoginDTO;
import com.bringup.member.user.dto.UserLoginTokenDTO;
import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.security.Principal;
import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final JoinService joinService;
    private final UserLoginService userLoginService;
    private final UserRepository userRepository;
    private final MemberService memberService;
    private final ErrorResponseHandler errorResponseHandler;

    @PostMapping("/name")
    public ResponseEntity<BfResponse<?>> getMemberName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userName = memberService.getUserName(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, userName));
    }

    @PutMapping("/mem")
    public ResponseEntity<BfResponse<?>> updateMember(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Map<String, String> requestBody){
        memberService.updateMember(userDetails, requestBody);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Member update successful")));
    }

    @DeleteMapping("/mem")
    public ResponseEntity<BfResponse<?>> deleteMember(@AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.deleteMember(userDetails);
        return ResponseEntity.ok(new BfResponse<>(SUCCESS, Map.of("message", "Member update successful")));
    }

    @GetMapping("/memberInfo/post")
    public ResponseEntity<BfResponse<?>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            UserEntity user = memberService.getMemberInfo(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, user));
        }catch (MemberException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/userLogin")
    public ResponseEntity<BfResponse<UserLoginTokenDTO>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        // 디버그 로그 추가
        System.out.println("Controller: login method called with " + loginDTO.getUserEmail());
        return ResponseEntity.ok(new BfResponse<>(userLoginService.login(loginDTO)));
        /*  로직 설명
        1.LoginController에서 로그인 요청 처리
    LoginController의 login 메서드가 요청을 받습니다.
    login 메서드는 전달된 UserLoginDTO 객체(로그인 정보가 포함된 DTO)를 사용하여 UserLoginService의 login 메서드를 호출합니다.
    LoginController는 UserLoginService로부터 반환된 UserLoginTokenDTO를 클라이언트에 응답합니다.
     */
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
