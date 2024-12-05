package com.bringup.member.page;


import com.bringup.common.security.service.UserDetailsImpl;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberPageController {

    @GetMapping("/Login")
    public String loginPage(){return "member/user/login";}

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

    @GetMapping("/company/list")
    public String companyPage(){
        return "member/user/company/companyList";
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
    public String announcementRecruitment() {
        return "member/user/profile/applyCV";
    }

    @GetMapping("/proposeRecruitment")
    public String proposeRecruitment() {
        return "member/user/recruitment/proposeRecruitment";
    }

    @GetMapping("/visitRecruitment")
    public String visitRecruitment() { return "member/user/recruitment/recruitmentVisit"; }

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

    @GetMapping("/companyReview/{companyId}")
    public String companyReview(@PathVariable("companyId") int companyId) {
        return "member/user/review/companyReview";
    }

    @GetMapping("/interviewReview/{companyId}")
    public String interviewReview(@PathVariable("companyId") int companyId) {
        return "member/user/review/interviewReview";
    }

    @GetMapping("/notice")
    public String notice() {
        return "member/user/notice/notice";
    }

    @GetMapping("/userNotice")
    public String userNotice() { return "member/user/notice/userNotice"; }

    @GetMapping("/createNotice")
    public String createNotice() { return "member/user/notice/createNotice"; }

    @GetMapping("/noticeDetail")
    public String noticeDetail() { return "member/user/notice/noticeDetail"; }

    @GetMapping("/memberProfile")
    public String memberProfile(){
        return "member/user/profile/memberProfile";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(){ return "member/user/profile/updateProfile"; }

    @GetMapping("/recruitment/details/{recruitmentIndex}")
    public String recruitmentDetails(@PathVariable("recruitmentIndex") int recruitmentId) {
        return "member/user/recruitment/detail";
    }

    @GetMapping("/freelancer")
    public String freelancer() {
        return "member/user/career/freelancer";
    }

    @GetMapping("/m_reviewDetail/{reviewId}")
    public String m_reviewDetail(@PathVariable("reviewId") int reviewId)
    {
        return "member/user/review/companyReviewDetail";
    }
    @GetMapping("/createReview")
    public String createReview()
    {
        return "member/user/review/createReview";
    }

    @GetMapping("/createInterviewReview")
    public String createInterviewReview()
    {
        return "member/user/review/createInterviewReview";
    }

    @GetMapping("/myReview")
    public String myReview() {return "member/user/review/myReview";}

    @GetMapping("/editReview")
    public String editReview()
    {
        return "member/user/review/editReview";
    }

    @GetMapping("/myCareer")
    public String myCareer() {return "member/user/career/myCareer";}

    @GetMapping("/git")
    public String myGit() {return "member/user/career/git";}

    @GetMapping("/notion")
    public String myNotion() {return "member/user/career/notion";}

    @GetMapping("/blog")
    public String myBlog() {return "member/user/career/blog";}

    @GetMapping("/file")
    public String myFile() {return "member/user/career/file";}

    @GetMapping("/letterWrite")
    public String letterWrite() {return "member/user/career/letterWrite";}

    @GetMapping("/blogList")
    public String blogList() {return "member/user/career/blogList";}

    @GetMapping("/record")
    public String recordList() {return "member/user/career/record";}

    @GetMapping("/awards")
    public String awards() {return "member/user/career/awards";}

    @GetMapping("/career")
    public String career() {return "member/user/career/career";}

    @GetMapping("/careerDetail")
    public String careerDetail() {return "member/user/career/careerInfo";}


    @GetMapping("/careerList")
    public String careerList() {return "member/user/career/careerList";}



    @GetMapping("/bookmark")
    public String bookmark() {return "member/user/company/bookmark";}

    @GetMapping("/company/detail/{companyId}")
    public String companyDetail(@PathVariable("companyId") int companyId) {
        return "member/user/company/companyDetail";
    }

}
