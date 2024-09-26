package com.bringup.member.page;


import com.bringup.common.security.service.UserDetailsImpl;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberPageController {

    @GetMapping("/userLoginForm")
    public String loginP(){return "member/user/login";}

    @GetMapping("/main")
    public String mainPage(){return "common/main/main";}

    @GetMapping("/userMain")
    public String userMain(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if (userDetails == null) {
            return "redirect:/member/userLoginForm"; // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        }
        return "member/user/userMain";
    }
    @GetMapping("/join")
    public String joinProcess() {return "member/user/userJoin";} // 회원가입 완료 후 리다이렉트할 페이지}

    @GetMapping()
    public String companyPage(){
        return "member/company";
    }

    @GetMapping("/recruitmentPage")
    public String showRecruitmentListPage() {
        return "member/user/recruitment/recruitmentList";
    }

    @GetMapping("/topRecruitment")
    public String topRecruitment() {
        return "member/user/recruitment/topRecruitment";
    }

    @GetMapping("/confirmRecruitment")
    public String confirmRecruitment() {
        return "member/user/recruitment/confirmRecruitment";
    }

    @GetMapping("/AnnouncementRecruitment")
    public String AnnouncementRecruitment() {
        return "member/user/recruitment/AnnouncementRecruitment";
    }

    @GetMapping("/proposeRecruitment")
    public String proposeRecruitment() {
        return "member/user/recruitment/proposeRecruitment";
    }

    @GetMapping("/potofolio")
    public String potofolio()
    {
        return "member/user/career/potofolio";
    }

    @GetMapping("/letter")
    public String letter()
    {
        return "member/user/career/letter";
    }

    @GetMapping("/resume")
    public String resume()
    {
        return "member/user/career/resume";
    }

    @GetMapping("/companyReview")
    public String companyReview() {
        return "member/user/review/companyReview";
    }

    @GetMapping("/interviewReview")
    public String interviewReview() {
        return "member/user/review/interviewReview";
    }

    @GetMapping("/notice")
    public String notice() {
        return "member/user/notice/notice";
    }

    @GetMapping("/memberProfile")
    public String memberProfile(){
        return "member/user/profile/memberProfile";
    }

}
