package com.bringup.company.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class PageController {

    @GetMapping("")
    public String mainPage(){
        return "company/main/company_main";
    }

    @GetMapping("/admin")
    public String adminP() {

        return "admin Controller";
    }

    @GetMapping("/jobpost/management")
    public String jobpostingManagement(){
        return "company/jobposting/management";
    }

    @GetMapping("/jobpost/registration")
    public String jobpostingRegistration(){
        return "company/jobposting/registration";
    }

    @GetMapping("/jobpost/detail")
    public String jobpostingDetail(){
        return "company/jobposting/detail";
    }

    @GetMapping("/jobpost/freelancer")
    public String freelancerDetail(){
        return "company/jobposting/freelancer";
    }

    @GetMapping("/product/management")
    public String productManagement(){
        return "company/product/management";
    }

    @GetMapping("/product/registration")
    public String productRegistration(){
        return "company/product/registration";
    }

    @GetMapping("/product/introduction")
    public String productAdvertisingBanner(){
        return "company/product/introduction";
    }

    @GetMapping("/product/ticket")
    public String productTicket(){
        return "company/product/ticket";
    }

    @GetMapping("/review/corporation")
    public String reviewCorporation(){
        return "company/review/corporation";
    }

    @GetMapping("/review/interview")
    public String reviewInterview(){
        return "company/review/interview";
    }

    @GetMapping("/recommendation")
    public String recommendation(){
        return "company/recommendation/recommendation";
    }

    @GetMapping("/recommendation_saved")
    public String recommendation_saved(){
        return "company/recommendation/recommendation_saved";
    }

    @GetMapping("/auth/profile")
    public String profile(){
        return "company/auth/profile/profile";
    }

    @GetMapping("/auth/login")
    public String login(){
        return "company/auth/login/login";
    }

    @GetMapping("/auth/signup")
    public String signup(){
        return "company/auth/signup/signup";
    }
    @GetMapping("/auth/signup/second")
    public String signupSecond(){
        return "company/auth/signup/signupsecond";
    }

    @GetMapping("/auth/updateProfile")
    public String updateProfile(){
        return "company/auth/profile/updateProfile";
    }

    @GetMapping("/auth/updateAuth")
    public String updateAuth(){
        return "company/auth/profile/updateAuth";
    }

    @GetMapping("/auth/findauth")
    public String findauth(){
        return "company/auth/findauth/findauth";
    }
}